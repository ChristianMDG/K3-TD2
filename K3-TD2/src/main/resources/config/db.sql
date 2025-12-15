create database mini_football_db;
create role mini_football_db_manager with password '123456' login;
grant connect on database mini_football_db to mini_football_db_manager;
grant create on database mini_football_db to mini_football_db_manager;

\c mini_football_db

grant usage,CREATE on SCHEMA public to mini_football_db_manager;
alter default privileges in schema public grant select,insert,update,delete on tables to mini_football_db_manager;
alter default privileges in schema public grant usage , select,update  on sequences to mini_football_db_manager;