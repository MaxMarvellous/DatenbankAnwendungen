REVOKE CREATE ON DATABASE db_klug16 FROM PUBLIC;
REVOKE CREATE ON SCHEMA webshop FROM PUBLIC;

CREATE ROLE WEBSHOP_READER_ROLE;
GRANT SELECT ON ALL TABLES IN SCHEMA webshop TO WEBSHOP_READER_ROLE;
GRANT SELECT ON  ALL SEQUENCES IN SCHEMA webshop TO WEBSHOP_READER_ROLE;


GRANT CONNECT ON DATABASE db_klug16 TO WEBSHOP_READER_ROLE;
GRANT USAGE ON SCHEMA webshop TO WEBSHOP_READER_ROLE;


CREATE ROLE WEBSHOP_WRITER_ROLE;
GRANT WEBSHOP_READER_ROLE TO WEBSHOP_WRITER_ROLE;
GRANT CREATE ON SCHEMA webshop TO WEBSHOP_WRITER_ROLE;
GRANT UPDATE ON ALL TABLES IN SCHEMA webshop TO WEBSHOP_WRITER_ROLE;
GRANT INSERT ON ALL TABLES IN SCHEMA webshop TO WEBSHOP_WRITER_ROLE;
GRANT UPDATE ON  ALL SEQUENCES IN SCHEMA webshop TO WEBSHOP_WRITER_ROLE;


CREATE ROLE WEBSHOP_ADMIN_ROLE;
GRANT WEBSHOP_WRITER_ROLE TO WEBSHOP_ADMIN_ROLE;
GRANT CREATE ON SCHEMA webshop TO WEBSHOP_ADMIN_ROLE;
GRANT DELETE ON ALL TABLES IN SCHEMA webshop TO WEBSHOP_ADMIN_ROLE;


CREATE USER webshop_florian WITH PASSWORD 'klug';
GRANT WEBSHOP_ADMIN_ROLE TO webshop_florian;

CREATE USER webshop_teamleader WITH PASSWORD 'teamleader';
GRANT WEBSHOP_WRITER_ROLE TO webshop_teamleader;

CREATE USER webshop_apprentice WITH PASSWORD 'apprentice';
GRANT WEBSHOP_READER_ROLE TO webshop_apprentice;
