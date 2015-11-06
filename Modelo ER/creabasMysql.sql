/*==============================================================*/
/* dbms name:      mysql 5.0                                    */
/* created on:     28/10/2015 12:24:44 p.m.                     */
/*==============================================================*/
drop schema if exists app_permission;
create schema app_permission;
use app_permission;

drop table if exists job_permissions;

drop table if exists parameter_configuratiions;

drop table if exists roles;

drop table if exists roll_permissions;

drop table if exists roll_perm_associate;

drop table if exists sessions;

drop table if exists users;

drop table if exists user_roll_associate;

/*==============================================================*/
/* table: job_permissions                                       */
/*==============================================================*/
create table job_permissions
(
   perm_id              bigint not null auto_increment,
   user_id              bigint not null,
   user_creator         bigint not null,
   user_updater         bigint,
   perm_application_date date not null,
   perm_init            timestamp not null,
   perm_end             timestamp not null,
   perm_description     text,
   perm_status          varchar(10) not null,
   primary key (perm_id)
);

/*==============================================================*/
/* table: parameter_configuratiions                             */
/*==============================================================*/
create table parameter_configuratiions
(
   param_id             bigint not null auto_increment,
   user_updater         bigint,
   param_name           varchar(30) not null,
   param_value          varchar(500) not null,
   primary key (param_id)
);

/*==============================================================*/
/* table: roles                                                 */
/*==============================================================*/
create table roles
(
   roll_id              bigint not null auto_increment,
   user_updater         bigint,
   user_creator         bigint not null,
   roll_name            varchar(30) not null,
   roll_description     text,
   roll_status          varchar(10) not null,
   primary key (roll_id)
);

/*==============================================================*/
/* table: roll_permissions                                      */
/*==============================================================*/
create table roll_permissions
(
   roll_perm_id         bigint not null auto_increment,
   user_creator         bigint not null,
   user_updater         bigint,
   roll_perm_name       varchar(30) not null,
   roll_perm_description text,
   roll_perm_status     varchar(10),
   primary key (roll_perm_id)
);

/*==============================================================*/
/* table: roll_perm_associate                                   */
/*==============================================================*/
create table roll_perm_associate
(
   roll_perm_id         bigint not null,
   roll_id              bigint not null,
   primary key (roll_perm_id, roll_id)
);

/*==============================================================*/
/* table: sessions                                              */
/*==============================================================*/
create table sessions
(
   username             varchar(30) not null,
   password             varchar(30) not null,
   last_login       timestamp default current_timestamp,
   primary key (username)
);

/*==============================================================*/
/* table: users                                                 */
/*==============================================================*/
create table users
(
   user_id              bigint not null auto_increment,
   username             varchar(30),
   manager              bigint,
   user_creator         bigint,
   user_updater         bigint,
   document_number      varchar(15) not null,
   document_type        varchar(2) not null,
   first_name           varchar(50) not null,
   last_name_one        varchar(30) not null,
   last_name_two        varchar(30),
   user_status          varchar(10) not null,
   primary key (user_id)
);

/*==============================================================*/
/* table: user_roll_associate                                   */
/*==============================================================*/
create table user_roll_associate
(
   roll_id              bigint not null,
   user_id              bigint not null,
   primary key (roll_id, user_id)
);

alter table job_permissions add constraint fk_perm_ucreator foreign key (user_creator)
      references users (user_id) on delete restrict on update restrict;

alter table job_permissions add constraint fk_perm_uupdater foreign key (user_updater)
      references users (user_id) on delete restrict on update restrict;

alter table job_permissions add constraint fk_user_perm_request foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;

alter table parameter_configuratiions add constraint fk_param_uupdater foreign key (user_updater)
      references users (user_id) on delete restrict on update restrict;

alter table roles add constraint fk_roll_ucreator foreign key (user_creator)
      references users (user_id) on delete restrict on update restrict;

alter table roles add constraint fk_roll_uupdater foreign key (user_updater)
      references users (user_id) on delete restrict on update restrict;

alter table roll_permissions add constraint fk_rperm_ucreator foreign key (user_creator)
      references users (user_id) on delete restrict on update restrict;

alter table roll_permissions add constraint fk_rperm_uupdater foreign key (user_updater)
      references users (user_id) on delete restrict on update restrict;

alter table roll_perm_associate add constraint fk_roll_perm_associate foreign key (roll_perm_id)
      references roll_permissions (roll_perm_id) on delete restrict on update restrict;

alter table roll_perm_associate add constraint fk_roll_perm_associate2 foreign key (roll_id)
      references roles (roll_id) on delete restrict on update restrict;

alter table users add constraint fk_manager foreign key (manager)
      references users (user_id) on delete restrict on update restrict;

alter table users add constraint fk_user_session_posee foreign key (username)
      references sessions (username) on delete restrict on update restrict;

alter table users add constraint fk_user_ucreator foreign key (user_creator)
      references users (user_id) on delete restrict on update restrict;

alter table users add constraint fk_user_uupdater foreign key (user_updater)
      references users (user_id) on delete restrict on update restrict;

alter table user_roll_associate add constraint fk_user_roll_associate foreign key (roll_id)
      references roles (roll_id) on delete restrict on update restrict;

alter table user_roll_associate add constraint fk_user_roll_associate2 foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;

