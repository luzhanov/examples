package filemerge;


import filemerge.service.FileSortingService;
import filemerge.service.ProductPriceMergeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static filemerge.Utils.*;


public class FileMergeApplication {

    private static FileSortingService sortingService;
    private static ProductPriceMergeService mergeService;

    public static void main(String[] args) {
        //todo: move all filenames to variables & pass them via command-line

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/context.xml");

        sortingService = (FileSortingService) ctx.getBean("fileSortingService");
        mergeService = (ProductPriceMergeService) ctx.getBean("productPriceMergeService");

        System.out.println("Application started");

        sortSplittedFiles();
        mergeFilesIntoOne("mergedPrices.csv");
    }

    private static void sortSplittedFiles() {
        try (FileInputStream productsIs = createFileInputStreamViaClassloader("products1.csv");
             FileInputStream pricesIs = createFileInputStreamViaClassloader("prices1.csv");
             OutputStream sortedProductsOs = createFileOutputStream("productsSorted.csv");
             OutputStream sortedPricesOs = createFileOutputStream("pricesSorted.csv")) {

            System.out.println("Sorting started");
            sortingService.sortCsvFileByFirstField(productsIs, sortedProductsOs);
            System.out.println("Products file sorted");
            sortingService.sortCsvFileByFirstField(pricesIs, sortedPricesOs);
            System.out.println("Prices file sorted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeFilesIntoOne(String resultFileName) {
        try (FileInputStream sortedProductsIs = createFileInputStream("productsSorted.csv");
             FileInputStream sortedPricesIs = createFileInputStream("pricesSorted.csv");
             OutputStream mergedPricesOs = createFileOutputStream(resultFileName)) {
            System.out.println("Merging started");
            mergeService.mergeSortedProductPricesToCsvFile(sortedProductsIs, sortedPricesIs, mergedPricesOs);
            System.out.println("Merging finished, please check file -> " + resultFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
