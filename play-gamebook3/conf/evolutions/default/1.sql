# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table gamebook (
  id                        bigint not null,
  name                      varchar(255),
  last_update               timestamp not null,
  constraint pk_gamebook primary key (id))
;

create sequence gamebook_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists gamebook;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists gamebook_seq;

