CREATE TABLE IF NOT EXISTS organization (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version     INTEGER NOT NULL,
    name        VARCHAR(50) NOT NULL,
    full_name   VARCHAR(250) NOT NULL,
    inn         VARCHAR(10)  NOT NULL,
    kpp         VARCHAR(9)  NOT NULL,
    address     VARCHAR(250) NOT NULL,
    phone       VARCHAR(20),
    is_active   BOOLEAN,
    CONSTRAINT inn UNIQUE (inn)
);
    CREATE INDEX UX_ORGANIZATION_ID ON organization(id);
    CREATE INDEX UX_ORGANIZATION_INN ON organization(inn);
    CREATE INDEX IX_ORGANIZATION_NAME ON organization(name);
    CREATE INDEX IX_ORGANIZATION_PHONE ON organization(phone);

CREATE TABLE IF NOT EXISTS office (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version     INTEGER NOT NULL,
    name        VARCHAR(50) NOT NULL,
    address     VARCHAR(250) NOT NULL,
    phone       VARCHAR(20),
    is_active   BOOLEAN,
    org_id      INTEGER,
CONSTRAINT Organization_FKEY FOREIGN KEY(org_id) REFERENCES PUBLIC.Organization (id) ON DELETE CASCADE
);
    CREATE INDEX UX_OFFICE_ID ON office(id);
    CREATE INDEX UX_OFFICE_ORG_ID ON office(org_id);
    CREATE INDEX IX_OFFICE_NAME ON office(name);
    CREATE INDEX IX_OFFICE_PHONE ON office(phone);

CREATE TABLE IF NOT EXISTS country (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version     INTEGER NOT NULL,
    code        VARCHAR(20)  NOT NULL,
    name        VARCHAR(50)  NOT NULL,
);
    CREATE INDEX UX_COUNTRY_ID ON country(id);

CREATE TABLE IF NOT EXISTS document (
  id               INTEGER  PRIMARY KEY AUTO_INCREMENT,
  version          INTEGER NOT NULL,
  doc_number       VARCHAR(20),
  doc_date         DATE,
);
    CREATE INDEX UX_DOCUMENT_ID ON document(id);

CREATE TABLE IF NOT EXISTS doc_type (
    id          INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version     INTEGER NOT NULL,
    code        VARCHAR(20) NOT NULL,
    name        VARCHAR(250) NOT NULL,
    document_id INTEGER NOT NULL,
  CONSTRAINT document_FKEY FOREIGN KEY(document_id) REFERENCES PUBLIC.document (id)
);
    CREATE INDEX UX_DOC_TYPE_ID ON doc_type(id);
    CREATE INDEX UX_DOC_TYPE_DOCUMENT_ID ON doc_type(document_id);

CREATE TABLE IF NOT EXISTS user (
  id               INTEGER  PRIMARY KEY AUTO_INCREMENT,
  version          INTEGER NOT NULL,
  first_name       VARCHAR(50) NOT NULL,
  middle_name      VARCHAR(50),
  second_name      VARCHAR(50),
  possition        VARCHAR(50) NOT NULL,
  phone            VARCHAR(20),
  is_identified    BOOLEAN,
  office_id        INTEGER  NOT NULL,
  document_id      INTEGER  NOT NULL,
  country_id       INTEGER  NOT NULL,
  CONSTRAINT office_FKEY FOREIGN KEY(office_id) REFERENCES PUBLIC.office (id),
  CONSTRAINT country_FKEY FOREIGN KEY(country_id) REFERENCES PUBLIC.country (id),
  CONSTRAINT document2_FKEY FOREIGN KEY(document_id) REFERENCES PUBLIC.document (id)
);
    CREATE INDEX UX_USER_ID ON user(id);
    CREATE INDEX UX_USER_OFFICE_ID ON user(id);
    CREATE INDEX IX_USER_FIRST_NAME ON user(first_name);
    CREATE INDEX IX_USER_MIDDLE_NAME ON user(middle_name);
    CREATE INDEX IX_USER_SECOND_NAME ON user(second_name);
    CREATE INDEX IX_USER_POSSITION ON user(possition);
    CREATE INDEX UX_USER_COUNTRY_ID ON user(country_id);
    CREATE INDEX UX_USER_DOCUMENT_ID ON user(document_id);
