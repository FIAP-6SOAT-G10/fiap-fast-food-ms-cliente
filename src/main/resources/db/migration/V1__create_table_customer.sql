CREATE TABLE IF NOT EXISTS "customers"."customer"
(
  id               bigserial                NOT NULL,
  cpf              varchar(11)              NOT NULL,
  name             varchar(50)              NOT NULL,
  email            varchar(100)             NOT NULL,
  CONSTRAINT customer_pkey PRIMARY KEY (id)
);
