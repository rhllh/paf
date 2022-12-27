drop table if exists user;
create table user(
    user_id char(8) not null, 
    username varchar(128) not null,
    name varchar(128),

    primary key(user_id)

);

drop table if exists task;
create table task(
    task_id int auto_increment,
    description varchar(255) not null,
    priority enum('1','2','3') default '2',
    due_date date not null,
    user_id char(8) not null,

    primary key(task_id),
    constraint fk_user_id
		foreign key(user_id)
        references user(user_id)
    
);