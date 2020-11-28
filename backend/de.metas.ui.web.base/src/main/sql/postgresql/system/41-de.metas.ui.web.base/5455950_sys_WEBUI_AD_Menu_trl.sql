--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = backup, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: webui_ad_menu_trl; Type: TABLE; Schema: backup; Owner: metasfresh
--

CREATE TABLE webui_ad_menu_trl (
    ad_menu_id numeric(10,0),
    ad_language character varying(6),
    ad_client_id numeric(10,0),
    ad_org_id numeric(10,0),
    isactive character(1),
    created timestamp with time zone,
    createdby numeric(10,0),
    updated timestamp with time zone,
    updatedby numeric(10,0),
    name character varying(60),
    description character varying(255),
    istranslated character(1),
    webui_namebrowse character varying(60),
    webui_namenew character varying(60),
    webui_namenewbreadcrumb character varying(60)
);


ALTER TABLE webui_ad_menu_trl OWNER TO metasfresh;

--
-- Data for Name: webui_ad_menu_trl; Type: TABLE DATA; Schema: backup; Owner: metasfresh
--

INSERT INTO webui_ad_menu_trl VALUES (144, 'fr_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Menü', 'Verwalten des Menüs', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (145, 'fr_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Sprache', 'Sprachen verwalten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (147, 'fr_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Nutzer', 'Verwalten von Nutzern des Systems', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (150, 'fr_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Rolle - Verwaltung', 'Verwalten von Nutzer-Verantwortlichkeiten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (144, 'it_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Menü', 'Verwalten des Menüs', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (145, 'it_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Sprache', 'Sprachen verwalten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (147, 'it_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Nutzer', 'Verwalten von Nutzern des Systems', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (150, 'it_CH', 0, 0, 'Y', '2012-11-16 09:17:12.043344+01', 100, '2012-11-16 09:17:12.043344+01', 100, 'Rolle - Verwaltung', 'Verwalten von Nutzer-Verantwortlichkeiten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (144, 'en_GB', 0, 0, 'Y', '2015-07-09 16:35:16.702068+02', 0, '2015-07-09 16:35:16.702068+02', 0, 'Menü', 'Verwalten des Menüs', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (145, 'en_GB', 0, 0, 'Y', '2015-07-09 16:35:16.702068+02', 0, '2015-07-09 16:35:16.702068+02', 0, 'Sprache', 'Sprachen verwalten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (147, 'en_GB', 0, 0, 'Y', '2015-07-09 16:35:16.702068+02', 0, '2015-07-09 16:35:16.702068+02', 0, 'Nutzer', 'Verwalten von Nutzern des Systems', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (150, 'en_GB', 0, 0, 'Y', '2015-07-09 16:35:16.702068+02', 0, '2015-07-09 16:35:16.702068+02', 0, 'Rolle - Verwaltung', 'Verwalten von Nutzer-Verantwortlichkeiten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (144, 'de_CH', 0, 0, 'Y', '2016-01-15 12:03:07.073723+01', 0, '2016-01-15 12:03:07.073723+01', 0, 'Menü', 'Verwalten des Menüs', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (145, 'de_CH', 0, 0, 'Y', '2016-01-15 12:03:07.073723+01', 0, '2016-01-15 12:03:07.073723+01', 0, 'Sprache', 'Sprachen verwalten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (147, 'de_CH', 0, 0, 'Y', '2016-01-15 12:03:07.073723+01', 0, '2016-01-15 12:03:07.073723+01', 0, 'Nutzer', 'Verwalten von Nutzern des Systems', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (150, 'de_CH', 0, 0, 'Y', '2016-01-15 12:03:07.073723+01', 0, '2016-01-15 12:03:07.073723+01', 0, 'Rolle - Verwaltung', 'Verwalten von Nutzer-Verantwortlichkeiten', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (144, 'en_US', 0, 0, 'Y', '2016-03-02 10:19:09.77269+01', 100, '2016-02-26 10:01:13+01', 100, 'Menu', 'Maintain Menu', 'Y', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (145, 'en_US', 0, 0, 'Y', '2016-03-02 10:19:09.77269+01', 100, '2016-02-26 10:01:13+01', 100, 'Language', 'Maintain Languages', 'Y', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (147, 'en_US', 0, 0, 'Y', '2016-03-02 10:19:09.77269+01', 100, '2016-02-26 10:01:13+01', 100, 'User', 'Maintain Users of the system', 'Y', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (150, 'en_US', 0, 0, 'Y', '2016-03-02 10:19:09.77269+01', 100, '2016-02-26 10:01:13+01', 100, 'Role', 'Maintain User Responsibilities', 'Y', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000008, 'de_CH', 0, 0, 'Y', '2016-09-21 18:10:22+02', 100, '2016-09-21 18:10:22+02', 100, 'CRM', NULL, 'N', 'CRM', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000008, 'en_US', 0, 0, 'Y', '2016-09-21 18:10:22+02', 100, '2016-09-21 18:10:22+02', 100, 'CRM', NULL, 'N', 'CRM', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000017, 'de_CH', 0, 0, 'Y', '2016-09-21 18:17:19+02', 100, '2016-09-21 18:17:19+02', 100, 'Wareneingang', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000017, 'en_US', 0, 0, 'Y', '2016-09-21 18:17:19+02', 100, '2016-09-21 18:17:19+02', 100, 'Wareneingang', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000021, 'de_CH', 0, 0, 'Y', '2016-09-21 18:23:12+02', 100, '2016-09-21 18:23:12+02', 100, 'Geschäftspartner', 'Geschäftspartner verwalten', 'N', 'Geschäftspartner', 'Neuer Geschäftspartner', 'Geschäftspartner');
INSERT INTO webui_ad_menu_trl VALUES (1000021, 'en_US', 0, 0, 'Y', '2016-09-21 18:23:12+02', 100, '2016-09-21 18:23:12+02', 100, 'Geschäftspartner', 'Geschäftspartner verwalten', 'N', 'Geschäftspartner', 'Neuer Geschäftspartner', 'Geschäftspartner');
INSERT INTO webui_ad_menu_trl VALUES (1000024, 'de_CH', 0, 0, 'Y', '2016-09-21 18:28:36+02', 100, '2016-09-21 18:28:36+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000024, 'en_US', 0, 0, 'Y', '2016-09-21 18:28:36+02', 100, '2016-09-21 18:28:36+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000025, 'de_CH', 0, 0, 'Y', '2016-09-21 18:29:04+02', 100, '2016-09-21 18:29:04+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000025, 'en_US', 0, 0, 'Y', '2016-09-21 18:29:04+02', 100, '2016-09-21 18:29:04+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000028, 'de_CH', 0, 0, 'Y', '2016-09-21 18:30:17+02', 100, '2016-09-21 18:30:17+02', 100, 'Anrede', 'Anreden verwalten', 'N', 'Anreden', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000028, 'en_US', 0, 0, 'Y', '2016-09-21 18:30:17+02', 100, '2016-09-21 18:30:17+02', 100, 'Anrede', 'Anreden verwalten', 'N', 'Anreden', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000029, 'de_CH', 0, 0, 'Y', '2016-09-21 18:31:27+02', 100, '2016-09-21 18:31:27+02', 100, 'Geschäftspartnergruppe', 'Geschäftspartnergruppen verwalten', 'N', 'Geschäftspartnergruppen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000029, 'en_US', 0, 0, 'Y', '2016-09-21 18:31:27+02', 100, '2016-09-21 18:31:27+02', 100, 'Geschäftspartnergruppe', 'Geschäftspartnergruppen verwalten', 'N', 'Geschäftspartnergruppen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000032, 'de_CH', 0, 0, 'Y', '2016-09-21 18:32:31+02', 100, '2016-09-21 18:32:31+02', 100, 'Zahlungsbedingung', 'Zahlungskonditionen verwalten', 'N', 'Zahlungsbedingungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000032, 'en_US', 0, 0, 'Y', '2016-09-21 18:32:31+02', 100, '2016-09-21 18:32:31+02', 100, 'Zahlungsbedingung', 'Zahlungskonditionen verwalten', 'N', 'Zahlungsbedingungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000035, 'de_CH', 0, 0, 'Y', '2016-09-21 18:34:25+02', 100, '2016-09-21 18:34:25+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000035, 'en_US', 0, 0, 'Y', '2016-09-21 18:34:25+02', 100, '2016-09-21 18:34:25+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000037, 'de_CH', 0, 0, 'Y', '2016-09-21 18:34:51+02', 100, '2016-09-21 18:34:51+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000037, 'en_US', 0, 0, 'Y', '2016-09-21 18:34:51+02', 100, '2016-09-21 18:34:51+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000041, 'de_CH', 0, 0, 'Y', '2016-09-22 15:42:26+02', 100, '2016-09-22 15:42:26+02', 100, 'Bestellung', 'Bestellungen eingeben und verwalten', 'N', 'Bestellungen', 'Neue Bestellung', 'Bestellung');
INSERT INTO webui_ad_menu_trl VALUES (1000041, 'en_US', 0, 0, 'Y', '2016-09-22 15:42:26+02', 100, '2016-09-22 15:42:26+02', 100, 'Bestellung', 'Bestellungen eingeben und verwalten', 'N', 'Bestellungen', 'Neue Bestellung', 'Bestellung');
INSERT INTO webui_ad_menu_trl VALUES (1000010, 'en_US', 0, 0, 'Y', '2016-09-21 18:14:53+02', 100, '2016-09-26 08:25:06.871+02', 100, 'Sales', NULL, 'Y', 'Sales', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000034, 'de_CH', 0, 0, 'Y', '2016-09-21 18:33:56+02', 100, '2016-09-21 18:33:56+02', 100, 'Produkt', 'Verwalten von Produkten', 'N', 'Produkte', 'Neues Produkt', 'Produkt');
INSERT INTO webui_ad_menu_trl VALUES (1000011, 'de_CH', 0, 0, 'Y', '2016-09-21 18:15:15+02', 100, '2016-09-21 18:15:15+02', 100, 'Beschaffung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000011, 'en_US', 0, 0, 'Y', '2016-09-21 18:15:15+02', 100, '2016-09-21 18:15:15+02', 100, 'Beschaffung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000009, 'de_CH', 0, 0, 'Y', '2016-09-21 18:14:19+02', 100, '2016-09-21 18:14:19+02', 100, 'Produkt Verwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000009, 'en_US', 0, 0, 'Y', '2016-09-21 18:14:19+02', 100, '2016-09-21 18:14:19+02', 100, 'Produkt Verwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000014, 'de_CH', 0, 0, 'Y', '2016-09-21 18:16:23+02', 100, '2016-09-21 18:16:23+02', 100, 'Produktion', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000014, 'en_US', 0, 0, 'Y', '2016-09-21 18:16:23+02', 100, '2016-09-21 18:16:23+02', 100, 'Produktion', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000013, 'de_CH', 0, 0, 'Y', '2016-09-21 18:16:05+02', 100, '2016-09-21 18:16:05+02', 100, 'Vertragsverwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000013, 'en_US', 0, 0, 'Y', '2016-09-21 18:16:05+02', 100, '2016-09-21 18:16:05+02', 100, 'Vertragsverwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000012, 'de_CH', 0, 0, 'Y', '2016-09-21 18:15:44+02', 100, '2016-09-21 18:15:44+02', 100, 'Lager Verwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000012, 'en_US', 0, 0, 'Y', '2016-09-21 18:15:44+02', 100, '2016-09-21 18:15:44+02', 100, 'Lager Verwaltung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000015, 'de_CH', 0, 0, 'Y', '2016-09-21 18:16:39+02', 100, '2016-09-21 18:16:39+02', 100, 'Finanzen', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000015, 'en_US', 0, 0, 'Y', '2016-09-21 18:16:39+02', 100, '2016-09-21 18:16:39+02', 100, 'Finanzen', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000016, 'de_CH', 0, 0, 'Y', '2016-09-21 18:16:57+02', 100, '2016-09-21 18:16:57+02', 100, 'Logistik', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000016, 'en_US', 0, 0, 'Y', '2016-09-21 18:16:57+02', 100, '2016-09-21 18:16:57+02', 100, 'Logistik', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000018, 'de_CH', 0, 0, 'Y', '2016-09-21 18:17:37+02', 100, '2016-09-21 18:17:37+02', 100, 'Fakturierung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000018, 'en_US', 0, 0, 'Y', '2016-09-21 18:17:37+02', 100, '2016-09-21 18:17:37+02', 100, 'Fakturierung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000019, 'de_CH', 0, 0, 'Y', '2016-09-21 18:18:16+02', 100, '2016-09-21 18:18:16+02', 100, 'Lieferung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000019, 'en_US', 0, 0, 'Y', '2016-09-21 18:18:16+02', 100, '2016-09-21 18:18:16+02', 100, 'Lieferung', NULL, 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000023, 'de_CH', 0, 0, 'Y', '2016-09-21 18:27:51+02', 100, '2016-09-21 18:27:51+02', 100, 'Aufgabe', 'Aufgaben verwalten', 'N', 'Aufgaben', 'Neue Aufgabe', 'Aufgabe');
INSERT INTO webui_ad_menu_trl VALUES (1000040, 'de_CH', 0, 0, 'Y', '2016-09-22 15:41:44+02', 100, '2016-09-22 15:41:44+02', 100, 'Auftrag', 'Aufträge anlegen und ändern', 'N', 'Aufträge', 'Neuer Auftrag', 'Auftrag');
INSERT INTO webui_ad_menu_trl VALUES (1000034, 'en_US', 0, 0, 'Y', '2016-09-21 18:33:56+02', 100, '2016-09-21 18:33:56+02', 100, 'Produkt', 'Verwalten von Produkten', 'N', 'Produkte', 'Neues Produkt', 'Produkt');
INSERT INTO webui_ad_menu_trl VALUES (1000010, 'de_CH', 0, 0, 'Y', '2016-09-21 18:14:53+02', 100, '2016-09-22 18:40:04.922+02', 100, 'Vertrieb', NULL, 'N', 'Vertrieb CH', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000023, 'en_US', 0, 0, 'Y', '2016-09-21 18:27:51+02', 100, '2016-09-21 18:27:51+02', 100, 'Aufgabe', 'Aufgaben verwalten', 'N', 'Aufgaben', 'Neue Aufgabe', 'Aufgabe');
INSERT INTO webui_ad_menu_trl VALUES (1000042, 'de_CH', 0, 0, 'Y', '2016-09-22 15:43:09+02', 100, '2016-09-22 15:43:09+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000042, 'en_US', 0, 0, 'Y', '2016-09-22 15:43:09+02', 100, '2016-09-22 15:43:09+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000044, 'de_CH', 0, 0, 'Y', '2016-09-22 15:44:14+02', 100, '2016-09-22 15:44:14+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000044, 'en_US', 0, 0, 'Y', '2016-09-22 15:44:14+02', 100, '2016-09-22 15:44:14+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000046, 'de_CH', 0, 0, 'Y', '2016-09-22 15:44:56+02', 100, '2016-09-22 15:44:56+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000046, 'en_US', 0, 0, 'Y', '2016-09-22 15:44:56+02', 100, '2016-09-22 15:44:56+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000047, 'de_CH', 0, 0, 'Y', '2016-09-22 15:45:23+02', 100, '2016-09-22 15:45:23+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000047, 'en_US', 0, 0, 'Y', '2016-09-22 15:45:23+02', 100, '2016-09-22 15:45:23+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000048, 'de_CH', 0, 0, 'Y', '2016-09-22 15:46:08+02', 100, '2016-09-22 15:46:08+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000048, 'en_US', 0, 0, 'Y', '2016-09-22 15:46:08+02', 100, '2016-09-22 15:46:08+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000049, 'de_CH', 0, 0, 'Y', '2016-09-22 15:46:23+02', 100, '2016-09-22 15:46:23+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000049, 'en_US', 0, 0, 'Y', '2016-09-22 15:46:23+02', 100, '2016-09-22 15:46:23+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000050, 'de_CH', 0, 0, 'Y', '2016-09-22 15:46:46+02', 100, '2016-09-22 15:46:46+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000050, 'en_US', 0, 0, 'Y', '2016-09-22 15:46:46+02', 100, '2016-09-22 15:46:46+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000051, 'de_CH', 0, 0, 'Y', '2016-09-22 15:47:09+02', 100, '2016-09-22 15:47:09+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000051, 'en_US', 0, 0, 'Y', '2016-09-22 15:47:09+02', 100, '2016-09-22 15:47:09+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000052, 'de_CH', 0, 0, 'Y', '2016-09-22 15:48:26+02', 100, '2016-09-22 15:48:26+02', 100, 'Lager und Lagerort', 'Verwalten von Lagern und Lagerorten', 'N', 'Lager', 'Neues Lager', 'Lager');
INSERT INTO webui_ad_menu_trl VALUES (1000052, 'en_US', 0, 0, 'Y', '2016-09-22 15:48:26+02', 100, '2016-09-22 15:48:26+02', 100, 'Lager und Lagerort', 'Verwalten von Lagern und Lagerorten', 'N', 'Lager', 'Neues Lager', 'Lager');
INSERT INTO webui_ad_menu_trl VALUES (1000053, 'de_CH', 0, 0, 'Y', '2016-09-22 15:48:42+02', 100, '2016-09-22 15:48:42+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000053, 'en_US', 0, 0, 'Y', '2016-09-22 15:48:42+02', 100, '2016-09-22 15:48:42+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000054, 'de_CH', 0, 0, 'Y', '2016-09-22 15:48:58+02', 100, '2016-09-22 15:48:58+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000054, 'en_US', 0, 0, 'Y', '2016-09-22 15:48:58+02', 100, '2016-09-22 15:48:58+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000055, 'de_CH', 0, 0, 'Y', '2016-09-22 15:49:11+02', 100, '2016-09-22 15:49:11+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000055, 'en_US', 0, 0, 'Y', '2016-09-22 15:49:11+02', 100, '2016-09-22 15:49:11+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000056, 'de_CH', 0, 0, 'Y', '2016-09-22 15:49:46+02', 100, '2016-09-22 15:49:46+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000056, 'en_US', 0, 0, 'Y', '2016-09-22 15:49:46+02', 100, '2016-09-22 15:49:46+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000057, 'de_CH', 0, 0, 'Y', '2016-09-22 15:49:58+02', 100, '2016-09-22 15:49:58+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000057, 'en_US', 0, 0, 'Y', '2016-09-22 15:49:58+02', 100, '2016-09-22 15:49:58+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000058, 'de_CH', 0, 0, 'Y', '2016-09-22 15:50:09+02', 100, '2016-09-22 15:50:09+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000058, 'en_US', 0, 0, 'Y', '2016-09-22 15:50:09+02', 100, '2016-09-22 15:50:09+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000059, 'de_CH', 0, 0, 'Y', '2016-09-22 15:50:20+02', 100, '2016-09-22 15:50:20+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000059, 'en_US', 0, 0, 'Y', '2016-09-22 15:50:20+02', 100, '2016-09-22 15:50:20+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000060, 'de_CH', 0, 0, 'Y', '2016-09-22 15:50:41+02', 100, '2016-09-22 15:50:41+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000060, 'en_US', 0, 0, 'Y', '2016-09-22 15:50:41+02', 100, '2016-09-22 15:50:41+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000061, 'de_CH', 0, 0, 'Y', '2016-09-22 15:51:25+02', 100, '2016-09-22 15:51:25+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000061, 'en_US', 0, 0, 'Y', '2016-09-22 15:51:25+02', 100, '2016-09-22 15:51:25+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000062, 'de_CH', 0, 0, 'Y', '2016-09-22 15:51:39+02', 100, '2016-09-22 15:51:39+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000062, 'en_US', 0, 0, 'Y', '2016-09-22 15:51:39+02', 100, '2016-09-22 15:51:39+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000063, 'de_CH', 0, 0, 'Y', '2016-09-22 15:51:52+02', 100, '2016-09-22 15:51:52+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000063, 'en_US', 0, 0, 'Y', '2016-09-22 15:51:52+02', 100, '2016-09-22 15:51:52+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000064, 'de_CH', 0, 0, 'Y', '2016-09-22 15:52:06+02', 100, '2016-09-22 15:52:06+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000064, 'en_US', 0, 0, 'Y', '2016-09-22 15:52:06+02', 100, '2016-09-22 15:52:06+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000065, 'de_CH', 0, 0, 'Y', '2016-09-22 15:52:18+02', 100, '2016-09-22 15:52:18+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000065, 'en_US', 0, 0, 'Y', '2016-09-22 15:52:18+02', 100, '2016-09-22 15:52:18+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000066, 'de_CH', 0, 0, 'Y', '2016-09-22 15:52:49+02', 100, '2016-09-22 15:52:49+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000066, 'en_US', 0, 0, 'Y', '2016-09-22 15:52:49+02', 100, '2016-09-22 15:52:49+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000067, 'de_CH', 0, 0, 'Y', '2016-09-22 15:53:06+02', 100, '2016-09-22 15:53:06+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000067, 'en_US', 0, 0, 'Y', '2016-09-22 15:53:06+02', 100, '2016-09-22 15:53:06+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000068, 'de_CH', 0, 0, 'Y', '2016-09-22 15:53:21+02', 100, '2016-09-22 15:53:21+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000068, 'en_US', 0, 0, 'Y', '2016-09-22 15:53:21+02', 100, '2016-09-22 15:53:21+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000069, 'de_CH', 0, 0, 'Y', '2016-09-22 15:53:41+02', 100, '2016-09-22 15:53:41+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000069, 'en_US', 0, 0, 'Y', '2016-09-22 15:53:41+02', 100, '2016-09-22 15:53:41+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000070, 'de_CH', 0, 0, 'Y', '2016-09-22 15:53:52+02', 100, '2016-09-22 15:53:52+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000070, 'en_US', 0, 0, 'Y', '2016-09-22 15:53:52+02', 100, '2016-09-22 15:53:52+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000071, 'de_CH', 0, 0, 'Y', '2016-09-22 15:54:08+02', 100, '2016-09-22 15:54:08+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000071, 'en_US', 0, 0, 'Y', '2016-09-22 15:54:08+02', 100, '2016-09-22 15:54:08+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000072, 'de_CH', 0, 0, 'Y', '2016-09-22 15:54:29+02', 100, '2016-09-22 15:54:29+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000072, 'en_US', 0, 0, 'Y', '2016-09-22 15:54:29+02', 100, '2016-09-22 15:54:29+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000075, 'de_CH', 0, 0, 'Y', '2016-09-22 15:55:46+02', 100, '2016-09-22 15:55:46+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000075, 'en_US', 0, 0, 'Y', '2016-09-22 15:55:46+02', 100, '2016-09-22 15:55:46+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000076, 'de_CH', 0, 0, 'Y', '2016-09-22 15:56:03+02', 100, '2016-09-22 15:56:03+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000076, 'en_US', 0, 0, 'Y', '2016-09-22 15:56:03+02', 100, '2016-09-22 15:56:03+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000077, 'de_CH', 0, 0, 'Y', '2016-09-22 15:56:17+02', 100, '2016-09-22 15:56:17+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000077, 'en_US', 0, 0, 'Y', '2016-09-22 15:56:17+02', 100, '2016-09-22 15:56:17+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000079, 'de_CH', 0, 0, 'Y', '2016-09-22 15:56:54+02', 100, '2016-09-22 15:56:54+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000079, 'en_US', 0, 0, 'Y', '2016-09-22 15:56:54+02', 100, '2016-09-22 15:56:54+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000080, 'de_CH', 0, 0, 'Y', '2016-09-22 16:01:06+02', 100, '2016-09-22 16:01:06+02', 100, 'Laufender Vertrag', 'Erlaubt die Erfassung von Pauschalen-Verträgen sowie von Leistungen, die ggf. Pauschal abgerechnet werden können.', 'N', 'Verträge', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000080, 'en_US', 0, 0, 'Y', '2016-09-22 16:01:06+02', 100, '2016-09-22 16:01:06+02', 100, 'Laufender Vertrag', 'Erlaubt die Erfassung von Pauschalen-Verträgen sowie von Leistungen, die ggf. Pauschal abgerechnet werden können.', 'N', 'Verträge', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000082, 'de_CH', 0, 0, 'Y', '2016-09-22 16:05:39+02', 100, '2016-09-22 16:05:39+02', 100, 'Hauptbuch Journal', 'Eingeben und Ändern von manuellen Journal-Einträgen', 'N', 'Hauptbuch Journale', 'Neues Hauptbuch Journal', 'Hauptbuch Journal');
INSERT INTO webui_ad_menu_trl VALUES (1000082, 'en_US', 0, 0, 'Y', '2016-09-22 16:05:39+02', 100, '2016-09-22 16:05:39+02', 100, 'Hauptbuch Journal', 'Eingeben und Ändern von manuellen Journal-Einträgen', 'N', 'Hauptbuch Journale', 'Neues Hauptbuch Journal', 'Hauptbuch Journal');
INSERT INTO webui_ad_menu_trl VALUES (1000085, 'de_CH', 0, 0, 'Y', '2016-09-22 16:10:24+02', 100, '2016-09-22 16:10:24+02', 100, 'Debitoren Rechnung', 'Eingabe von Rechnungen an Kunden', 'N', 'Debitoren Rechnungen', 'Neue Debitoren Rechnung', 'Debitoren Rechnung');
INSERT INTO webui_ad_menu_trl VALUES (1000085, 'en_US', 0, 0, 'Y', '2016-09-22 16:10:24+02', 100, '2016-09-22 16:10:24+02', 100, 'Debitoren Rechnung', 'Eingabe von Rechnungen an Kunden', 'N', 'Debitoren Rechnungen', 'Neue Debitoren Rechnung', 'Debitoren Rechnung');
INSERT INTO webui_ad_menu_trl VALUES (1000081, 'de_CH', 0, 0, 'Y', '2016-09-22 16:01:47+02', 100, '2016-09-22 16:01:47+02', 100, 'Produktionsauftrag', 'Produktionsauftrag', 'N', 'Produktionsaufträge', 'Neuer Produktionsauftrag', 'Produktionsauftrag');
INSERT INTO webui_ad_menu_trl VALUES (1000081, 'en_US', 0, 0, 'Y', '2016-09-22 16:01:47+02', 100, '2016-09-22 16:01:47+02', 100, 'Produktionsauftrag', 'Produktionsauftrag', 'N', 'Produktionsaufträge', 'Neuer Produktionsauftrag', 'Produktionsauftrag');
INSERT INTO webui_ad_menu_trl VALUES (1000083, 'de_CH', 0, 0, 'Y', '2016-09-22 16:08:16+02', 100, '2016-09-22 16:08:16+02', 100, 'Wareneingang', 'Wareneingang von Lieferanten', 'N', 'Wareneingänge', 'Neuer Wareneingang', 'Wareneingang');
INSERT INTO webui_ad_menu_trl VALUES (1000083, 'en_US', 0, 0, 'Y', '2016-09-22 16:08:16+02', 100, '2016-09-22 16:08:16+02', 100, 'Wareneingang', 'Wareneingang von Lieferanten', 'N', 'Wareneingänge', 'Neuer Wareneingang', 'Wareneingang');
INSERT INTO webui_ad_menu_trl VALUES (1000086, 'de_CH', 0, 0, 'Y', '2016-09-22 16:11:39+02', 100, '2016-09-22 16:11:39+02', 100, 'Kreditoren Rechnung', 'Eingabe von Rechnungen von Lieferanten', 'N', 'Kreditoren Rechnungen', 'Neue Kreditoren Rechnung', 'Kreditoren Rechnung');
INSERT INTO webui_ad_menu_trl VALUES (1000086, 'en_US', 0, 0, 'Y', '2016-09-22 16:11:39+02', 100, '2016-09-22 16:11:39+02', 100, 'Kreditoren Rechnung', 'Eingabe von Rechnungen von Lieferanten', 'N', 'Kreditoren Rechnungen', 'Neue Kreditoren Rechnung', 'Kreditoren Rechnung');
INSERT INTO webui_ad_menu_trl VALUES (1000087, 'de_CH', 0, 0, 'Y', '2016-09-22 17:46:51+02', 100, '2016-09-22 17:46:51+02', 100, 'Lieferung', 'Warenlieferungen an den Kunden und Rücksendungen durch den Kunden', 'N', 'Lieferungen', 'Neue Lieferung', 'Lieferung');
INSERT INTO webui_ad_menu_trl VALUES (1000087, 'en_US', 0, 0, 'Y', '2016-09-22 17:46:51+02', 100, '2016-09-22 17:46:51+02', 100, 'Lieferung', 'Warenlieferungen an den Kunden und Rücksendungen durch den Kunden', 'N', 'Lieferungen', 'Neue Lieferung', 'Lieferung');
INSERT INTO webui_ad_menu_trl VALUES (1000088, 'de_CH', 0, 0, 'Y', '2016-09-22 17:49:15+02', 100, '2016-09-22 17:49:15+02', 100, 'Preise', 'Verwalten von Produkt-Preislisten', 'N', 'Preise', 'Neuer Preis', 'Preis');
INSERT INTO webui_ad_menu_trl VALUES (1000088, 'en_US', 0, 0, 'Y', '2016-09-22 17:49:15+02', 100, '2016-09-22 17:49:15+02', 100, 'Preise', 'Verwalten von Produkt-Preislisten', 'N', 'Preise', 'Neuer Preis', 'Preis');
INSERT INTO webui_ad_menu_trl VALUES (1000089, 'de_CH', 0, 0, 'Y', '2016-09-22 17:50:31+02', 100, '2016-09-22 17:50:31+02', 100, 'Merkmale', 'Produkt-Merkmal', 'N', 'Merkmale', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000089, 'en_US', 0, 0, 'Y', '2016-09-22 17:50:31+02', 100, '2016-09-22 17:50:31+02', 100, 'Merkmale', 'Produkt-Merkmal', 'N', 'Merkmale', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000090, 'de_CH', 0, 0, 'Y', '2016-09-22 17:55:29+02', 100, '2016-09-22 17:55:29+02', 100, 'Partner Verküpfungen', 'Beziehungen der Geschäftspartner verwalten', 'N', 'Partner Verküpfungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000090, 'en_US', 0, 0, 'Y', '2016-09-22 17:55:29+02', 100, '2016-09-22 17:55:29+02', 100, 'Partner Verküpfungen', 'Beziehungen der Geschäftspartner verwalten', 'N', 'Partner Verküpfungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000091, 'de_CH', 0, 0, 'Y', '2016-09-22 17:56:31+02', 100, '2016-09-22 17:56:31+02', 100, 'Aufgaben Typ', 'Maintain Request Types', 'N', 'Aufgaben Typ', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000091, 'en_US', 0, 0, 'Y', '2016-09-22 17:56:31+02', 100, '2016-09-22 17:56:31+02', 100, 'Aufgaben Typ', 'Maintain Request Types', 'N', 'Aufgaben Typ', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000092, 'de_CH', 0, 0, 'Y', '2016-09-22 18:02:18+02', 100, '2016-09-22 18:02:18+02', 100, 'Stückliste', 'Maintain Product Bill of Materials & Formula ', 'N', 'Stücklisten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000092, 'en_US', 0, 0, 'Y', '2016-09-22 18:02:18+02', 100, '2016-09-22 18:02:18+02', 100, 'Stückliste', 'Maintain Product Bill of Materials & Formula ', 'N', 'Stücklisten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000093, 'de_CH', 0, 0, 'Y', '2016-09-22 18:03:40+02', 100, '2016-09-22 18:03:40+02', 100, 'Rabatte', 'Rabatt-Schemata verwalten', 'N', 'Rabatte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000093, 'en_US', 0, 0, 'Y', '2016-09-22 18:03:40+02', 100, '2016-09-22 18:03:40+02', 100, 'Rabatte', 'Rabatt-Schemata verwalten', 'N', 'Rabatte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000094, 'de_CH', 0, 0, 'Y', '2016-09-22 18:05:54+02', 100, '2016-09-22 18:05:54+02', 100, 'Preisberechnung', 'Defines a list of pricing and discount rules that need to be invoked when product price is calculated.', 'N', 'Preisberechnung', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000094, 'en_US', 0, 0, 'Y', '2016-09-22 18:05:54+02', 100, '2016-09-22 18:05:54+02', 100, 'Preisberechnung', 'Defines a list of pricing and discount rules that need to be invoked when product price is calculated.', 'N', 'Preisberechnung', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000095, 'de_CH', 0, 0, 'Y', '2016-09-22 18:07:08+02', 100, '2016-09-22 18:07:08+02', 100, 'Preis Schema', 'Preislisten-Schemata verwalten', 'N', 'Preis Schemata', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000095, 'en_US', 0, 0, 'Y', '2016-09-22 18:07:08+02', 100, '2016-09-22 18:07:08+02', 100, 'Preis Schema', 'Preislisten-Schemata verwalten', 'N', 'Preis Schemata', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000096, 'de_CH', 0, 0, 'Y', '2016-09-22 18:09:12+02', 100, '2016-09-22 18:09:12+02', 100, 'Produktgruppe', NULL, 'N', 'Produktgruppen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000096, 'en_US', 0, 0, 'Y', '2016-09-22 18:09:12+02', 100, '2016-09-22 18:09:12+02', 100, 'Produktgruppe', NULL, 'N', 'Produktgruppen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000097, 'de_CH', 0, 0, 'Y', '2016-09-22 18:10:54+02', 100, '2016-09-22 18:10:54+02', 100, 'Auftragskandidaten', NULL, 'N', 'Auftragskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000097, 'en_US', 0, 0, 'Y', '2016-09-22 18:10:54+02', 100, '2016-09-22 18:10:54+02', 100, 'Auftragskandidaten', NULL, 'N', 'Auftragskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000040, 'en_US', 0, 0, 'Y', '2016-09-22 15:41:44+02', 100, '2016-09-26 08:26:33.264+02', 100, 'Sales Order', 'Create and edit Sales Orders', 'N', 'Sales Order', 'New Sales Order', 'Auftrag');
INSERT INTO webui_ad_menu_trl VALUES (1000098, 'de_CH', 0, 0, 'Y', '2016-09-26 09:03:28+02', 100, '2016-09-26 09:03:28+02', 100, 'System', NULL, 'N', 'System', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000098, 'en_US', 0, 0, 'Y', '2016-09-26 09:03:28+02', 100, '2016-09-26 09:03:28+02', 100, 'System', NULL, 'N', 'System', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000099, 'de_CH', 0, 0, 'Y', '2016-09-26 09:04:04+02', 100, '2016-09-26 09:04:04+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000099, 'en_US', 0, 0, 'Y', '2016-09-26 09:04:04+02', 100, '2016-09-26 09:04:04+02', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000100, 'de_CH', 0, 0, 'Y', '2016-09-26 09:04:22+02', 100, '2016-09-26 09:04:22+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000100, 'en_US', 0, 0, 'Y', '2016-09-26 09:04:22+02', 100, '2016-09-26 09:04:22+02', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000101, 'de_CH', 0, 0, 'Y', '2016-09-26 09:04:49+02', 100, '2016-09-26 09:04:49+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000101, 'en_US', 0, 0, 'Y', '2016-09-26 09:04:49+02', 100, '2016-09-26 09:04:49+02', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000104, 'de_CH', 0, 0, 'Y', '2016-09-29 07:28:29+02', 100, '2016-09-29 07:28:29+02', 100, 'Rechnungskandidaten', 'Fenster enthält so genannte "Rechnungskandidaten", die vor der Rechnungsstellung noch angepasst werden können', 'N', 'Rechnungskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (1000104, 'en_US', 0, 0, 'Y', '2016-09-29 07:28:29+02', 100, '2016-09-29 07:28:29+02', 100, 'Rechnungskandidaten', 'Fenster enthält so genannte "Rechnungskandidaten", die vor der Rechnungsstellung noch angepasst werden können', 'N', 'Rechnungskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540756, 'en_US', 0, 0, 'Y', '2017-01-31 17:58:10+01', 100, '2017-01-31 17:58:10+01', 100, 'Preissystem', NULL, 'N', 'Preissystem', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540757, 'de_CH', 0, 0, 'Y', '2017-01-31 18:12:39+01', 100, '2017-01-31 18:12:39+01', 100, 'Preisliste', NULL, 'N', 'Preisliste', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540757, 'en_US', 0, 0, 'Y', '2017-01-31 18:12:39+01', 100, '2017-01-31 18:12:39+01', 100, 'Preisliste', NULL, 'N', 'Preisliste', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540758, 'de_CH', 0, 0, 'Y', '2017-02-01 17:07:40+01', 100, '2017-02-01 17:07:40+01', 100, 'Mahnungen', NULL, 'N', 'Mahnungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540758, 'en_US', 0, 0, 'Y', '2017-02-01 17:07:40+01', 100, '2017-02-01 17:07:40+01', 100, 'Mahnungen', NULL, 'N', 'Mahnungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540759, 'de_CH', 0, 0, 'Y', '2017-02-01 17:08:34+01', 100, '2017-02-01 17:08:34+01', 100, 'Mahndisposition', NULL, 'N', 'Mahndisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540759, 'en_US', 0, 0, 'Y', '2017-02-01 17:08:34+01', 100, '2017-02-01 17:08:34+01', 100, 'Mahndisposition', NULL, 'N', 'Mahndisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540743, 'de_CH', 0, 0, 'Y', '2016-11-22 07:38:59+01', 100, '2016-11-22 07:38:59+01', 100, 'User Dashboard (WebUI)', NULL, 'N', 'User Dashboard', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540743, 'en_US', 0, 0, 'Y', '2016-11-22 07:38:59+01', 100, '2016-11-22 07:38:59+01', 100, 'User Dashboard (WebUI)', NULL, 'N', 'User Dashboard', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540728, 'de_CH', 0, 0, 'Y', '2016-10-18 16:04:40+02', 100, '2016-10-18 16:04:40+02', 100, 'Lieferdisposition', 'Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt änderungen bzgl. Priorität und Stückzahl.', 'N', 'Lieferdisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540728, 'en_US', 0, 0, 'Y', '2016-10-18 16:04:40+02', 100, '2016-10-18 16:04:40+02', 100, 'Lieferdisposition', 'Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt änderungen bzgl. Priorität und Stückzahl.', 'N', 'Lieferdisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540736, 'de_CH', 0, 0, 'Y', '2016-11-08 07:28:22+01', 100, '2016-11-08 07:28:22+01', 100, 'Wareneingangsdisposition', NULL, 'N', 'Wareneingangsdisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540736, 'en_US', 0, 0, 'Y', '2016-11-08 07:28:22+01', 100, '2016-11-08 07:28:22+01', 100, 'Wareneingangsdisposition', NULL, 'N', 'Wareneingangsdisposition', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540738, 'de_CH', 0, 0, 'Y', '2016-11-10 15:46:04+01', 100, '2016-11-10 15:46:04+01', 100, 'Umsatzreport Geschäftspartner', NULL, 'N', 'Umsatzreport Geschäftspartner', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540738, 'en_US', 0, 0, 'Y', '2016-11-10 15:46:04+01', 100, '2016-11-10 15:46:04+01', 100, 'Umsatzreport Geschäftspartner', NULL, 'N', 'Umsatzreport Geschäftspartner', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540749, 'de_CH', 0, 0, 'Y', '2017-01-24 14:27:34+01', 100, '2017-01-24 14:27:34+01', 100, 'Zahlung', 'Verarbeiten von Zahlungen und Quittungen', 'N', 'Zahlungen', 'neue Zahlung', 'Zahlung');
INSERT INTO webui_ad_menu_trl VALUES (540749, 'en_US', 0, 0, 'Y', '2017-01-24 14:27:34+01', 100, '2017-01-24 14:27:34+01', 100, 'Zahlung', 'Verarbeiten von Zahlungen und Quittungen', 'N', 'Zahlungen', 'neue Zahlung', 'Zahlung');
INSERT INTO webui_ad_menu_trl VALUES (540752, 'de_CH', 0, 0, 'Y', '2017-01-31 17:54:30+01', 100, '2017-01-31 17:54:30+01', 100, 'Preise', NULL, 'N', 'Preise', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540752, 'en_US', 0, 0, 'Y', '2017-01-31 17:54:30+01', 100, '2017-01-31 17:54:30+01', 100, 'Preise', NULL, 'N', 'Preise', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540753, 'de_CH', 0, 0, 'Y', '2017-01-31 17:55:09+01', 100, '2017-01-31 17:55:09+01', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540753, 'en_US', 0, 0, 'Y', '2017-01-31 17:55:09+01', 100, '2017-01-31 17:55:09+01', 100, 'Aktionen', NULL, 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540754, 'de_CH', 0, 0, 'Y', '2017-01-31 17:55:33+01', 100, '2017-01-31 17:55:33+01', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540754, 'en_US', 0, 0, 'Y', '2017-01-31 17:55:33+01', 100, '2017-01-31 17:55:33+01', 100, 'Berichte', NULL, 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540755, 'de_CH', 0, 0, 'Y', '2017-01-31 17:55:54+01', 100, '2017-01-31 17:55:54+01', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540755, 'en_US', 0, 0, 'Y', '2017-01-31 17:55:54+01', 100, '2017-01-31 17:55:54+01', 100, 'Einstellungen', NULL, 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540756, 'de_CH', 0, 0, 'Y', '2017-01-31 17:58:10+01', 100, '2017-01-31 17:58:10+01', 100, 'Preissystem', NULL, 'N', 'Preissystem', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540760, 'de_CH', 0, 0, 'Y', '2017-02-01 17:09:08+01', 100, '2017-02-01 17:09:08+01', 100, 'Mahnart', 'Mahnstufen verwalten', 'N', 'Mahnart', NULL, NULL);
INSERT INTO webui_ad_menu_trl VALUES (540760, 'en_US', 0, 0, 'Y', '2017-02-01 17:09:08+01', 100, '2017-02-01 17:09:08+01', 100, 'Mahnart', 'Mahnstufen verwalten', 'N', 'Mahnart', NULL, NULL);


--
-- PostgreSQL database dump complete
--

