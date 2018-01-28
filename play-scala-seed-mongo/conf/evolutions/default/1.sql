# Person schema

# --- !Ups
create table `person` (`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
`firstName` TEXT NOT NULL,`lastName` TEXT
NOT NULL)

# --- !Downs
drop table `person`