create  type  enum_position as enum('GK','DEF','MIDF','STR');
create type enum_continent as enum  ('AFRICA','EUROPA','ASIA','AMERICA');
create table if not exists Team(
id serial primary key,
name varchar(100),
continent enum_continent
);

create table if not exists Player (
id serial primary key not null ,
name varchar(100),
age int,
position enum_position,
id_team int references Team(id) on delete cascade
);
