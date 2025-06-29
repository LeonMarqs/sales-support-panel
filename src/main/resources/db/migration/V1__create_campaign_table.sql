create table campaigns(
   id uuid not null,
   name varchar(255) not null,
   budget decimal(10,2) not null,
   start_date date,
   CONSTRAINT pk_campaign PRIMARY KEY (id)
);
