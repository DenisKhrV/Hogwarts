alter table student add constraint age_constraint_must_be_greater_than_sixteen check (age>=16);

alter table student add constraint name_must_be_unique unique (name);
alter table student alter column name set not null;

alter table faculty add constraint name_and_color_must_be_unique unique (name, color);

alter table student alter column age set default 20;