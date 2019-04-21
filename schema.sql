create table account(
id SERIAL PRIMARY KEY,
name varchar(300) UNIQUE,
email varchar(300) UNIQUE,
phone varchar(300) UNIQUE,
password varchar(300) NOT NULL
);
create table event(
id SERIAL PRIMARY KEY,
owner_id int NOT NULL REFERENCES account(id),
title varchar(300) NOT NULL,
thumbnail varchar(300) NOT NULL,
content varchar NOT NULL,
place varchar(300) NOT NULL,
start_date date NOT NULL,
end_date date NOT NULL
);
create table ticket(
id SERIAL PRIMARY KEY,
max int NOT NULL,
event_id int REFERENCES event(id) ON DELETE CASCADE,
name varchar(300) NOT NULL,
description varchar(300) NOT NULL,
price int NOT NULL,
sales_start date NOT NULL,
sales_end date NOT NULL
);
create table applicant(
event_id int REFERENCES event(id),
ticket_id int REFERENCES ticket(id),
user_id int REFERENCES account(id),
PRIMARY KEY (event_id, user_id)
);




create table organization(
id SERIAL PRIMARY KEY,
name varchar(300) NOT NULL
);
create table organization_member(
user_id int NOT NULL REFERENCES account(id),
org_id int NOT NULL REFERENCES organization(id),
PRIMARY KEY (org_id, user_id)
)