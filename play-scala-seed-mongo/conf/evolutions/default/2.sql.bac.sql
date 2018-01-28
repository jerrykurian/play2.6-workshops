# Role schema

# --- !Ups
create table `role` (`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` TEXT NOT NULL)

create table `person_role` (`person_id` BIGINT NOT NULL,
`role_id` BIGINT NOT NULL
)

# --- !Downs
drop table `role`
drop table `person_role`