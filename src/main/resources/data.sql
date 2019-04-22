INSERT INTO ORGANIZATION (ID, VERSION, NAME, FULL_NAME, INN, KPP, ADDRESS, PHONE, IS_ACTIVE) VALUES (1, 999, 'ООО СТАРТ', 'ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕСТВЕННОСТЬЮ СТАРТ', '5820001122', '582001001', 'ПЕНЗА УЛ.КРАСНАЯ Д. 1', '541234', TRUE);

INSERT INTO ORGANIZATION (ID, VERSION, NAME, FULL_NAME, INN, KPP, ADDRESS, PHONE, IS_ACTIVE) VALUES (2, 999, 'ЗАО МИР', 'ЗАКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО МИР', '5820003344', '582001001', 'ПЕНЗА ПР.ПОБЕДЫ Д. 9', '551234', TRUE);

INSERT INTO ORGANIZATION (ID, VERSION, NAME, FULL_NAME, INN, KPP, ADDRESS, PHONE, IS_ACTIVE) VALUES (3, 999, 'ПО ПОЛЁТ', 'ПРОИЗВОДСТВЕННОЕ ОБЪЕДИНЕНИЕ ПОЛЁТ', '5820005566', '582001002', 'ПЕНЗА ПР.СТРОИТЕЛЕЙ Д. 11', '654321', TRUE);

INSERT INTO OFFICE (ID, VERSION, NAME, ADDRESS, PHONE, IS_ACTIVE, ORG_ID) VALUES (1, 999, 'АДМИНИСТРАЦИЯ', 'Г.ПЕНЗА УЛ. ЛЕНИНА Д.777', '84125565', TRUE, 1);

INSERT INTO OFFICE (ID, VERSION, NAME, ADDRESS, PHONE, IS_ACTIVE, ORG_ID) VALUES (2, 999, 'БУХГАЛТЕРИЯ', 'Г.ПЕНЗА УЛ. ВОРОШИЛОВА ', '841255667', TRUE, 1);

INSERT INTO OFFICE (ID, VERSION, NAME, ADDRESS, PHONE, IS_ACTIVE, ORG_ID) VALUES (3, 999, 'АДМИНИСТРАЦИЯ', 'Г.САМАРА УЛ. ВИШНЕВАЯ Д.34', '84699001', TRUE, 2);

INSERT INTO OFFICE (ID, VERSION, NAME, ADDRESS, PHONE, IS_ACTIVE, ORG_ID) VALUES (4, 999, 'БУХГАЛТЕРИЯ', 'Г.САМАРА УЛ. ОКРУЖНАЯ Д.35', '84699007', TRUE, 2);

INSERT INTO OFFICE (ID, VERSION, NAME, ADDRESS, PHONE, IS_ACTIVE, ORG_ID) VALUES (5, 999, 'АДМИНИСТРАЦИЯ', 'Г.МОСКВА УЛ. РЕВОЛЮЦИОННАЯ Д.7', '94512345', TRUE, 3);

INSERT INTO COUNTRY (ID, VERSION, NAME, CODE) VALUES (1, 999, 'Российская Федерация', 643);

INSERT INTO COUNTRY (ID, VERSION, NAME, CODE) VALUES (2, 999, 'Республика Беларусь', 112);

INSERT INTO COUNTRY (ID, VERSION, NAME, CODE) VALUES (3, 999, 'Республика Казахстан', 395);

INSERT INTO DOC_TYPE(ID, VERSION,  NAME, CODE) VALUES (1, 999, 'Паспорт гражданина Российской Федерации', 21);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (2, 999, 'Военный билет РФ', 7);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (3, 999, 'Временное удостоверение, выданное взамен военного билета РФ', 8);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (4, 999, 'Свидетельство о признании лица беженцем РФ', 11);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (5, 999, 'Вид на жительство в РФ', 12);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (6, 999, 'Удостоверение беженца РФ', 13);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (7, 999, 'Разрешение на временное проживание в РФ', 14);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (8, 999, 'Свидетельство о предоставлении временного убежища РФ', 15);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (9, 999, 'Свидетельство о рождении РФ', 3);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (10, 999, 'Паспорт гражданина Республики Беларусь образца 1996 года', 1);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (11, 999, 'Паспорт Республики Беларусь образца 1993 года (Беларусь)', 2);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (12, 999, 'Паспорт СССР образца 1974 года (Беларусь)', 3);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (13, 999, 'Национальный паспорт иностранного гражданина (Беларусь)', 4);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (14, 999, 'Свидетельство о рождении (Беларусь)', 5);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (15, 999, 'Иной документ, удостоверяющий личность (Беларусь)', 6);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (16, 999, 'Справка об освобождении (Беларусь)', 7);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (17, 999, 'Удостоверение беженца (Беларусь)', 8);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (18, 999, 'Вид на жительство в Республике Беларусь', 9);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (19, 999, 'Удостоверение лица без гражданства (Беларусь)', 10);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (20, 999, 'Удостоверение беженца (Беларусь)', 11);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (21, 999, 'Паспорт гражданина Республики Казахстан', 5);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (22, 999, 'Дипломатический паспорт Республики Казахстан', 6);

INSERT INTO DOC_TYPE(ID, VERSION, NAME, CODE) VALUES (23, 999, 'Свидетельстов о рождении Республики Казахстан', 7);


INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (1, 999, '565999', '2002-01-15', 1);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (2, 999, '555999', '2012-10-11', 2);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (3, 999, '555900', '2014-12-31', 3);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (4, 999, '555910', '2015-06-19', 4);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (5, 999, '522900', '2017-11-12', 5);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (6, 999, '522007', '2007-04-19', 6);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (7, 999, '522008', '2017-01-29', 7);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (8, 999, '522901', '2012-03-03', 8);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (9, 999, '522900', '2017-07-23', 9);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (10, 999, '533900', '2015-02-28', 11);

INSERT INTO DOCUMENT (ID, VERSION, NUMBER, DATE, DOC_TYPE_ID) VALUES (11, 999, '522203', '2016-09-27', 21);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (1, 999, 'ИВАН', 'ИВАНОВИЧ', 'ИВАНОВ', 'ГЕНЕРАЛЬНЫЙ ДИРЕКТОР', '94512345', TRUE, 1, 1, 1);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (2, 999, 'ПЁТР', 'ПЁТРОВИЧ', 'ПЕТРОВ', 'ДИРЕКТОР', '94512346', TRUE, 1, 1, 2);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (3, 999, 'СЕМЁН', 'СЕМЁНОВИЧ', 'СЕМЁНОВ', 'ЗАМЕСТИТЕЛЬ ДИРЕКТОРА', '94512340', TRUE, 1, 1, 3);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (4, 999, 'ДМИТРИЙ', 'ФЕДОРОВИЧ', 'ШАЦ', 'ЗАМЕСТИТЕЛЬ ДИРЕКТОРА', '94512340', TRUE, 2, 1, 4);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (5, 999, 'ЕЛИЗАВЕТА', 'АРКАДЬЕВНА', 'ФЕДОРЧУК', 'ГЛАВНЫЙ БУХГАЛТЕР', '9451842', TRUE, 2, 1, 5);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (6, 999, 'ЮСТАС', 'АЛЕКСЕЕВИЧ', 'ИСАЕВ', 'ГЛАВНЫЙ БУХГАЛТЕР', '8421842', TRUE, 3, 1, 6);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (7, 999, 'НИКОЛАЙ', 'ДМИТРИЕВИЧ', 'ХЛЕСТАКОВ', 'БУХГАЛТЕР', '8421841', TRUE, 3, 2, 7);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (8, 999, 'ДУГЛАС', 'КУСТАНАЙВИЧ', 'САБОЙНЕС', 'РУКОВОДИТЕЛЬ ОТДЕЛА', '9451842', TRUE, 4, 2, 8);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (9, 999, 'АЙСЕДОРА', 'АХМАТОВНА', 'ПУШКИНА', 'ГЛАВНЫЙ БУХГАЛТЕР', '8422842', TRUE, 4, 2, 9);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (10, 999, 'ТИМУР', 'БОРИСОВИЧ', 'ЖУК', 'ЗАМЕСТИТЕЛЬ РУКОВОДИТЕЛЯ', '9400842', TRUE, 5, 3, 10);

INSERT INTO USER (ID, VERSION, FIRST_NAME, MIDDLE_NAME, SECOND_NAME, POSSITION, PHONE, IS_IDENTIFIED, OFFICE_ID, COUNTRY_ID, DOCUMENT_ID) VALUES (11, 999, 'МАРАТ', 'КАЙРАТОВИЧ', 'КАРИМОВ', 'БУХГАЛТЕР', '9451842', TRUE, 5, 3, 11);




