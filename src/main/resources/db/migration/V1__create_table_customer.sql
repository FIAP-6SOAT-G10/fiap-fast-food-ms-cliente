CREATE TABLE IF NOT EXISTS "tech-challenge"."cliente"
(
  id               bigserial                NOT NULL,
  cpf              varchar(11)              NOT NULL,
  nome             varchar(50)              NOT NULL,
  email            varchar(100)             NOT NULL,
  CONSTRAINT cliente_pkey PRIMARY KEY (id)
);