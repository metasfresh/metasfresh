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
-- Name: webui_ad_menu; Type: TABLE; Schema: backup; Owner: metasfresh
--

CREATE TABLE webui_ad_menu (
    ad_menu_id numeric(10,0),
    ad_client_id numeric(10,0),
    ad_org_id numeric(10,0),
    isactive character(1),
    created timestamp with time zone,
    createdby numeric(10,0),
    updated timestamp with time zone,
    name character varying(60),
    updatedby numeric(10,0),
    description character varying(255),
    issummary character(1),
    issotrx character(1),
    isreadonly character(1),
    action character(1),
    ad_window_id numeric(10,0),
    ad_workflow_id numeric(10,0),
    ad_task_id numeric(10,0),
    ad_process_id numeric(10,0),
    ad_form_id numeric(10,0),
    ad_workbench_id numeric(10,0),
    entitytype character varying(40),
    internalname character varying(100),
    iscreatenew character(1),
    webui_namebrowse character varying(60),
    webui_namenew character varying(60),
    webui_namenewbreadcrumb character varying(60)
);


ALTER TABLE webui_ad_menu OWNER TO metasfresh;

--
-- Data for Name: webui_ad_menu; Type: TABLE DATA; Schema: backup; Owner: metasfresh
--

INSERT INTO webui_ad_menu VALUES (144, 0, 0, 'Y', '1999-05-21 00:00:00+02', 0, '2000-01-02 00:00:00+01', 'Menü', 0, 'Verwalten des Menüs', 'N', 'Y', 'N', 'W', 105, NULL, NULL, NULL, NULL, NULL, 'D', '144 (Todo: Set Internal Name for UI testing)', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu VALUES (145, 0, 0, 'Y', '1999-05-21 00:00:00+02', 0, '2000-01-02 00:00:00+01', 'Sprache', 0, 'Sprachen verwalten', 'N', 'Y', 'N', 'W', 106, NULL, NULL, NULL, NULL, NULL, 'D', '145 (Todo: Set Internal Name for UI testing)', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu VALUES (147, 0, 0, 'Y', '1999-06-07 00:00:00+02', 0, '2005-11-22 11:37:02+01', 'Nutzer', 100, 'Verwalten von Nutzern des Systems', 'N', 'Y', 'N', 'W', 108, NULL, NULL, NULL, NULL, NULL, 'D', '147 (Todo: Set Internal Name for UI testing)', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu VALUES (150, 0, 0, 'Y', '1999-06-11 00:00:00+02', 0, '2009-09-09 18:09:46+02', 'Rolle - Verwaltung', 100, 'Verwalten von Nutzer-Verantwortlichkeiten', 'N', 'Y', 'N', 'W', 111, NULL, NULL, NULL, NULL, NULL, 'D', '150 (Todo: Set Internal Name for UI testing)', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000008, 0, 0, 'Y', '2016-09-21 18:10:22+02', 100, '2016-09-21 18:10:22+02', 'CRM', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'CRM', 'N', 'CRM', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000011, 0, 0, 'Y', '2016-09-21 18:15:15+02', 100, '2016-09-22 18:18:14+02', 'Beschaffung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Beschaffung', 'N', 'Beschaffung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000009, 0, 0, 'Y', '2016-09-21 18:14:19+02', 100, '2016-09-22 18:19:16+02', 'Produktverwaltung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Produktverwaltung', 'N', 'Produktverwaltung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000010, 0, 0, 'Y', '2016-09-21 18:14:53+02', 100, '2016-09-26 08:24:42+02', 'Vertrieb', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Vertrieb', 'N', 'Vertrieb', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000017, 0, 0, 'Y', '2016-09-21 18:17:19+02', 100, '2016-09-21 18:17:19+02', 'Wareneingang', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Wareneingang', 'N', NULL, NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000013, 0, 0, 'Y', '2016-09-21 18:16:05+02', 100, '2016-09-22 18:18:31+02', 'Vertragsverwaltung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Vertragsverwaltung', 'N', 'Vertragsverwaltung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000014, 0, 0, 'Y', '2016-09-21 18:16:23+02', 100, '2016-09-22 18:19:24+02', 'Produktion', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Produktion', 'N', 'Produktion', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000015, 0, 0, 'Y', '2016-09-21 18:16:39+02', 100, '2016-09-22 18:19:32+02', 'Finanzen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Finanzen', 'N', 'Finanzen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000016, 0, 0, 'Y', '2016-09-21 18:16:57+02', 100, '2016-09-22 18:19:38+02', 'Logistik', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Logistik', 'N', 'Logistik', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000019, 0, 0, 'Y', '2016-09-21 18:18:16+02', 100, '2016-09-22 18:19:51+02', 'Lieferung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Lieferung', 'N', 'Lieferung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000021, 0, 0, 'Y', '2016-09-21 18:23:12+02', 100, '2016-09-21 18:23:12+02', 'Geschäftspartner', 100, 'Geschäftspartner verwalten', 'N', 'N', 'N', 'W', 123, NULL, NULL, NULL, NULL, NULL, 'de.metas.handlingunits', '_Geschäftspartner', 'Y', 'Geschäftspartner', 'Neuer Geschäftspartner', 'Geschäftspartner');
INSERT INTO webui_ad_menu VALUES (1000024, 0, 0, 'Y', '2016-09-21 18:28:36+02', 100, '2016-09-21 18:28:36+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Berichte', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000025, 0, 0, 'Y', '2016-09-21 18:29:04+02', 100, '2016-09-21 18:29:04+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Einstellungen', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000028, 0, 0, 'Y', '2016-09-21 18:30:17+02', 100, '2016-09-21 18:30:17+02', 'Anrede', 100, 'Anreden verwalten', 'N', 'N', 'N', 'W', 178, NULL, NULL, NULL, NULL, NULL, 'U', '_Anrede', 'N', 'Anreden', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000029, 0, 0, 'Y', '2016-09-21 18:31:27+02', 100, '2016-09-21 18:31:27+02', 'Geschäftspartnergruppe', 100, 'Geschäftspartnergruppen verwalten', 'N', 'N', 'N', 'W', 192, NULL, NULL, NULL, NULL, NULL, 'de.metas.handlingunits', '192 (Todo: Set Internal Name for UI testing)', 'N', 'Geschäftspartnergruppen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000032, 0, 0, 'Y', '2016-09-21 18:32:31+02', 100, '2016-09-21 18:32:31+02', 'Zahlungsbedingung', 100, 'Zahlungskonditionen verwalten', 'N', 'N', 'N', 'W', 141, NULL, NULL, NULL, NULL, NULL, 'U', '_Zahlungsbedingung', 'N', 'Zahlungsbedingungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000035, 0, 0, 'Y', '2016-09-21 18:34:25+02', 100, '2016-09-21 18:34:25+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000037, 0, 0, 'Y', '2016-09-21 18:34:51+02', 100, '2016-09-21 18:34:51+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000041, 0, 0, 'Y', '2016-09-22 15:42:26+02', 100, '2016-09-22 15:42:26+02', 'Bestellung', 100, 'Bestellungen eingeben und verwalten', 'N', 'N', 'N', 'W', 181, NULL, NULL, NULL, NULL, NULL, 'de.metas.procurement', '_Bestellung', 'Y', 'Bestellungen', 'Neue Bestellung', 'Bestellung');
INSERT INTO webui_ad_menu VALUES (1000042, 0, 0, 'Y', '2016-09-22 15:43:09+02', 100, '2016-09-22 15:43:09+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Aktionen', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000044, 0, 0, 'Y', '2016-09-22 15:44:14+02', 100, '2016-09-22 15:44:14+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000046, 0, 0, 'Y', '2016-09-22 15:44:56+02', 100, '2016-09-22 15:45:04+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_1', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000047, 0, 0, 'Y', '2016-09-22 15:45:23+02', 100, '2016-09-22 15:45:23+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_2', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000048, 0, 0, 'Y', '2016-09-22 15:46:08+02', 100, '2016-09-22 15:46:08+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_1', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000049, 0, 0, 'Y', '2016-09-22 15:46:23+02', 100, '2016-09-22 15:46:23+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_2', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000050, 0, 0, 'Y', '2016-09-22 15:46:46+02', 100, '2016-09-22 15:46:46+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_1', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000051, 0, 0, 'Y', '2016-09-22 15:47:09+02', 100, '2016-09-22 15:47:09+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_2', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000052, 0, 0, 'Y', '2016-09-22 15:48:26+02', 100, '2016-09-22 15:48:26+02', 'Lager und Lagerort', 100, 'Verwalten von Lagern und Lagerorten', 'N', 'N', 'N', 'W', 139, NULL, NULL, NULL, NULL, NULL, 'de.metas.materialtracking.ch.lagerkonf', '_Lager', 'Y', 'Lager', 'Neues Lager', 'Lager');
INSERT INTO webui_ad_menu VALUES (1000053, 0, 0, 'Y', '2016-09-22 15:48:42+02', 100, '2016-09-22 15:48:42+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_3', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000054, 0, 0, 'Y', '2016-09-22 15:48:58+02', 100, '2016-09-22 15:48:58+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_4', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000055, 0, 0, 'Y', '2016-09-22 15:49:11+02', 100, '2016-09-22 15:49:11+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_5', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000056, 0, 0, 'Y', '2016-09-22 15:49:46+02', 100, '2016-09-22 15:49:46+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_6', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000057, 0, 0, 'Y', '2016-09-22 15:49:58+02', 100, '2016-09-22 15:49:58+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_7', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000058, 0, 0, 'Y', '2016-09-22 15:50:09+02', 100, '2016-09-22 15:50:09+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_8', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000059, 0, 0, 'Y', '2016-09-22 15:50:20+02', 100, '2016-09-22 15:50:20+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_9', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000060, 0, 0, 'Y', '2016-09-22 15:50:41+02', 100, '2016-09-22 15:50:41+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_10', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000061, 0, 0, 'Y', '2016-09-22 15:51:25+02', 100, '2016-09-22 15:51:25+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_3', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000062, 0, 0, 'Y', '2016-09-22 15:51:39+02', 100, '2016-09-22 15:51:39+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_4', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000063, 0, 0, 'Y', '2016-09-22 15:51:52+02', 100, '2016-09-22 15:51:52+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_5', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000064, 0, 0, 'Y', '2016-09-22 15:52:06+02', 100, '2016-09-22 15:52:06+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_6', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000065, 0, 0, 'Y', '2016-09-22 15:52:18+02', 100, '2016-09-22 15:52:18+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_7', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000066, 0, 0, 'Y', '2016-09-22 15:52:49+02', 100, '2016-09-22 15:52:49+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_8', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000067, 0, 0, 'Y', '2016-09-22 15:53:06+02', 100, '2016-09-22 15:53:06+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_9', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000068, 0, 0, 'Y', '2016-09-22 15:53:21+02', 100, '2016-09-22 15:53:21+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_10', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000069, 0, 0, 'Y', '2016-09-22 15:53:41+02', 100, '2016-09-22 15:53:41+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_3', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000070, 0, 0, 'Y', '2016-09-22 15:53:52+02', 100, '2016-09-22 15:53:52+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_4', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000071, 0, 0, 'Y', '2016-09-22 15:54:08+02', 100, '2016-09-22 15:54:08+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_5', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000072, 0, 0, 'Y', '2016-09-22 15:54:29+02', 100, '2016-09-22 15:54:29+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_6', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000075, 0, 0, 'Y', '2016-09-22 15:55:46+02', 100, '2016-09-22 15:55:46+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_8', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000076, 0, 0, 'Y', '2016-09-22 15:56:03+02', 100, '2016-09-22 15:56:03+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_9', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000077, 0, 0, 'Y', '2016-09-22 15:56:17+02', 100, '2016-09-22 15:56:17+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_10', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000079, 0, 0, 'Y', '2016-09-22 15:56:54+02', 100, '2016-09-22 15:56:54+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_11', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000081, 0, 0, 'Y', '2016-09-22 16:01:47+02', 100, '2016-09-22 16:01:47+02', 'Produktionsauftrag', 100, 'Produktionsauftrag', 'N', 'N', 'N', 'W', 53009, NULL, NULL, NULL, NULL, NULL, 'EE01', '53009 (Todo: Set Internal Name for UI testing)', 'Y', 'Produktionsaufträge', 'Neuer Produktionsauftrag', 'Produktionsauftrag');
INSERT INTO webui_ad_menu VALUES (1000080, 0, 0, 'Y', '2016-09-22 16:01:06+02', 100, '2016-09-22 16:01:59+02', 'Vertrag', 100, 'Erlaubt die Erfassung von Pauschalen-Verträgen sowie von Leistungen, die ggf. Pauschal abgerechnet werden können.', 'N', 'N', 'N', 'W', 540112, NULL, NULL, NULL, NULL, NULL, 'de.metas.flatrate', '_Vertrag', 'N', 'Verträge', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000082, 0, 0, 'Y', '2016-09-22 16:05:39+02', 100, '2016-09-22 16:05:39+02', 'Hauptbuch Journal', 100, 'Eingeben und Ändern von manuellen Journal-Einträgen', 'N', 'N', 'N', 'W', 132, NULL, NULL, NULL, NULL, NULL, 'de.metas.handlingunits', 'Hauptbuch Journal', 'Y', 'Hauptbuch Journale', 'Neues Hauptbuch Journal', 'Hauptbuch Journal');
INSERT INTO webui_ad_menu VALUES (1000083, 0, 0, 'Y', '2016-09-22 16:08:16+02', 100, '2016-09-22 16:08:16+02', 'Wareneingang', 100, 'Wareneingang von Lieferanten', 'N', 'N', 'N', 'W', 184, NULL, NULL, NULL, NULL, NULL, 'de.metas.inoutcandidate', '_Wareneingang', 'Y', 'Wareneingänge', 'Neuer Wareneingang', 'Wareneingang');
INSERT INTO webui_ad_menu VALUES (1000034, 0, 0, 'Y', '2016-09-21 18:33:56+02', 100, '2016-09-23 07:19:28+02', 'Produkte', 100, 'Verwalten von Produkten', 'N', 'N', 'N', 'W', 140, NULL, NULL, NULL, NULL, NULL, 'U', '_Produkt', 'Y', 'Produkte', 'Neues Produkt', 'Produkt');
INSERT INTO webui_ad_menu VALUES (1000040, 0, 0, 'Y', '2016-09-22 15:41:44+02', 100, '2016-09-22 18:30:06+02', 'Auftrag', 100, 'Aufträge anlegen und ändern', 'N', 'Y', 'N', 'W', 143, NULL, NULL, NULL, NULL, NULL, 'U', '_Auftrag', 'Y', 'Aufträge', 'Neuer Auftrag', 'Auftrag');
INSERT INTO webui_ad_menu VALUES (1000023, 0, 0, 'Y', '2016-09-21 18:27:51+02', 100, '2017-01-16 14:22:49+01', 'Vorgang', 100, 'Ansehen und bearbeiten aller Anfragen', 'N', 'N', 'N', 'W', 232, NULL, NULL, NULL, NULL, NULL, 'U', '_Vorgang', 'Y', 'Vorgänge', 'Neuer Vorgang', 'Vorgang');
INSERT INTO webui_ad_menu VALUES (1000085, 0, 0, 'Y', '2016-09-22 16:10:24+02', 100, '2016-09-22 16:10:24+02', 'Debitoren Rechnung', 100, 'Eingabe von Rechnungen an Kunden', 'N', 'N', 'N', 'W', 167, NULL, NULL, NULL, NULL, NULL, 'de.metas.invoicecandidate', 'Debitoren Rechnung', 'Y', 'Debitoren Rechnungen', 'Neue Debitoren Rechnung', 'Debitoren Rechnung');
INSERT INTO webui_ad_menu VALUES (1000086, 0, 0, 'Y', '2016-09-22 16:11:39+02', 100, '2016-09-22 16:11:39+02', 'Kreditoren Rechnung', 100, 'Eingabe von Rechnungen von Lieferanten', 'N', 'N', 'N', 'W', 183, NULL, NULL, NULL, NULL, NULL, 'de.metas.swat', 'Kreditoren Rechnung', 'Y', 'Kreditoren Rechnungen', 'Neue Kreditoren Rechnung', 'Kreditoren Rechnung');
INSERT INTO webui_ad_menu VALUES (1000087, 0, 0, 'Y', '2016-09-22 17:46:51+02', 100, '2016-09-22 17:46:51+02', 'Lieferung', 100, 'Warenlieferungen an den Kunden und Rücksendungen durch den Kunden', 'N', 'Y', 'N', 'W', 169, NULL, NULL, NULL, NULL, NULL, 'de.metas.materialtracking.ch.lagerkonf', '_Lieferung', 'Y', 'Lieferungen', 'Neue Lieferung', 'Lieferung');
INSERT INTO webui_ad_menu VALUES (1000088, 0, 0, 'Y', '2016-09-22 17:49:15+02', 100, '2016-09-22 17:49:15+02', 'Preise', 100, 'Verwalten von Produkt-Preislisten', 'N', 'N', 'N', 'W', 146, NULL, NULL, NULL, NULL, NULL, 'org.adempiere.pricing', '_Preise', 'Y', 'Preise', 'Neuer Preis', 'Preis');
INSERT INTO webui_ad_menu VALUES (1000089, 0, 0, 'Y', '2016-09-22 17:50:31+02', 100, '2016-09-22 17:50:31+02', 'Merkmale', 100, 'Produkt-Merkmal', 'N', 'N', 'N', 'W', 260, NULL, NULL, NULL, NULL, NULL, 'U', '_Merkmale', 'N', 'Merkmale', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000090, 0, 0, 'Y', '2016-09-22 17:55:29+02', 100, '2016-09-22 17:55:29+02', 'Partner Verküpfungen', 100, 'Beziehungen der Geschäftspartner verwalten', 'N', 'N', 'N', 'W', 313, NULL, NULL, NULL, NULL, NULL, 'de.metas.commission', '_Partner Verküpfungen', 'N', 'Partner Verküpfungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000091, 0, 0, 'Y', '2016-09-22 17:56:31+02', 100, '2016-09-22 17:56:31+02', 'Aufgaben Typ', 100, 'Maintain Request Types', 'N', 'N', 'N', 'W', 244, NULL, NULL, NULL, NULL, NULL, 'org.adempiere.rpl.requesthandler', '_Aufgaben Typ', 'N', 'Aufgaben Typ', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000092, 0, 0, 'Y', '2016-09-22 18:02:18+02', 100, '2016-09-22 18:02:18+02', 'Stückliste', 100, 'Maintain Product Bill of Materials & Formula ', 'N', 'N', 'N', 'W', 53006, NULL, NULL, NULL, NULL, NULL, 'EE01', '_Stückliste', 'N', 'Stücklisten', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000093, 0, 0, 'Y', '2016-09-22 18:03:40+02', 100, '2016-09-22 18:03:40+02', 'Rabatte', 100, 'Rabatt-Schemata verwalten', 'N', 'N', 'N', 'W', 233, NULL, NULL, NULL, NULL, NULL, 'U', 'Rabatte', 'N', 'Rabatte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000094, 0, 0, 'Y', '2016-09-22 18:05:54+02', 100, '2016-09-22 18:05:54+02', 'Preisberechnung', 100, 'Defines a list of pricing and discount rules that need to be invoked when product price is calculated.', 'N', 'N', 'N', 'W', 53189, NULL, NULL, NULL, NULL, NULL, 'org.adempiere.pricing', 'Preisberechnung', 'N', 'Preisberechnung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000095, 0, 0, 'Y', '2016-09-22 18:07:08+02', 100, '2016-09-22 18:07:08+02', 'Preis Schema', 100, 'Preislisten-Schemata verwalten', 'N', 'N', 'N', 'W', 337, NULL, NULL, NULL, NULL, NULL, 'org.adempiere.pricing', 'Preis Schema', 'N', 'Preis Schemata', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000096, 0, 0, 'Y', '2016-09-22 18:09:12+02', 100, '2016-09-22 18:09:12+02', 'Produktgruppe', 100, NULL, 'N', 'N', 'N', 'W', 540116, NULL, NULL, NULL, NULL, NULL, 'de.metas.invoicecandidate', 'Produktgruppe', 'N', 'Produktgruppen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000097, 0, 0, 'Y', '2016-09-22 18:10:54+02', 100, '2016-09-22 18:10:54+02', 'Auftragskandidaten', 100, NULL, 'N', 'N', 'N', 'W', 540095, NULL, NULL, NULL, NULL, NULL, 'de.metas.ordercandidate', 'Auftragskandidaten', 'N', 'Auftragskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000012, 0, 0, 'Y', '2016-09-21 18:15:44+02', 100, '2016-09-22 18:18:53+02', 'Lagerverwaltung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Lagerverwaltung', 'N', 'Lagerverwaltung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000018, 0, 0, 'Y', '2016-09-21 18:17:37+02', 100, '2016-09-22 18:19:45+02', 'Fakturierung', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'Fakturierung', 'N', 'Fakturierung', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000098, 0, 0, 'Y', '2016-09-26 09:03:28+02', 100, '2016-09-26 09:03:28+02', 'System', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', 'System', 'N', 'System', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000100, 0, 0, 'Y', '2016-09-26 09:04:22+02', 100, '2016-09-26 09:04:22+02', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_11', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000099, 0, 0, 'Y', '2016-09-26 09:04:04+02', 100, '2016-09-26 09:04:40+02', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_12', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000101, 0, 0, 'Y', '2016-09-26 09:04:49+02', 100, '2016-09-26 09:04:49+02', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_12', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (1000104, 0, 0, 'Y', '2016-09-29 07:28:29+02', 100, '2016-09-29 07:28:29+02', 'Rechnungskandidaten', 100, 'Fenster enthält so genannte "Rechnungskandidaten", die vor der Rechnungsstellung noch angepasst werden können', 'N', 'N', 'N', 'W', 540092, NULL, NULL, NULL, NULL, NULL, 'de.metas.invoicecandidate', 'Rechnungskandidaten', 'N', 'Rechnungskandidaten', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540736, 0, 0, 'Y', '2016-11-08 07:28:22+01', 100, '2016-11-08 07:28:22+01', 'Wareneingangsdisposition', 100, NULL, 'N', 'N', 'N', 'W', 540196, NULL, NULL, NULL, NULL, NULL, 'de.metas.inoutcandidate', '_Wareneingangsdisposition', 'N', 'Wareneingangsdisposition', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540728, 0, 0, 'Y', '2016-10-18 16:04:40+02', 100, '2016-10-18 16:04:40+02', 'Lieferdisposition', 100, 'Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt änderungen bzgl. Priorität und Stückzahl.', 'N', 'Y', 'N', 'W', 500221, NULL, NULL, NULL, NULL, NULL, 'de.metas.inoutcandidate', '_Lieferdisposition1', 'N', 'Lieferdisposition', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540738, 0, 0, 'Y', '2016-11-10 15:46:04+01', 100, '2016-11-10 15:46:04+01', 'Umsatzreport Geschäftspartner', 100, NULL, 'N', 'N', 'N', 'R', NULL, NULL, NULL, 540561, NULL, NULL, 'de.metas.fresh', '_Umsatzreport Geschäftspartner (Jasper)', 'N', 'Umsatzreport Geschäftspartner', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540743, 0, 0, 'Y', '2016-11-22 07:38:59+01', 100, '2016-11-22 07:38:59+01', 'User Dashboard (WebUI)', 100, NULL, 'N', 'N', 'N', 'W', 540317, NULL, NULL, NULL, NULL, NULL, 'de.metas.ui.web', 'User_Dashboard', 'N', 'User Dashboard', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540749, 0, 0, 'Y', '2017-01-24 14:27:34+01', 100, '2017-01-24 14:27:34+01', 'Zahlung', 100, 'Verarbeiten von Zahlungen und Quittungen', 'N', 'N', 'N', 'W', 195, NULL, NULL, NULL, NULL, NULL, 'de.metas.fresh', '_Zahlung', 'Y', 'Zahlungen', 'neue Zahlung', 'Zahlung');
INSERT INTO webui_ad_menu VALUES (540752, 0, 0, 'Y', '2017-01-31 17:54:30+01', 100, '2017-01-31 17:54:30+01', 'Preise', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Preise_webui', 'N', 'Preise', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540753, 0, 0, 'Y', '2017-01-31 17:55:09+01', 100, '2017-01-31 17:55:09+01', 'Aktionen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Aktionen_20', 'N', 'Aktionen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540754, 0, 0, 'Y', '2017-01-31 17:55:33+01', 100, '2017-01-31 17:55:33+01', 'Berichte', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Berichte_20', 'N', 'Berichte', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540755, 0, 0, 'Y', '2017-01-31 17:55:54+01', 100, '2017-01-31 17:55:54+01', 'Einstellungen', 100, NULL, 'Y', 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'U', '_Einstellungen_20', 'N', 'Einstellungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540756, 0, 0, 'Y', '2017-01-31 17:58:10+01', 100, '2017-01-31 18:11:59+01', 'Preissystem', 100, NULL, 'N', 'N', 'N', 'W', 540320, NULL, NULL, NULL, NULL, NULL, 'D', '_Preissystem', 'N', 'Preissystem', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540757, 0, 0, 'Y', '2017-01-31 18:12:39+01', 100, '2017-01-31 18:12:39+01', 'Preisliste', 100, NULL, 'N', 'N', 'N', 'W', 540321, NULL, NULL, NULL, NULL, NULL, 'de.metas.handlingunits', '_Preisliste', 'N', 'Preisliste', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540758, 0, 0, 'Y', '2017-02-01 17:07:40+01', 100, '2017-02-01 17:07:40+01', 'Mahnungen', 100, NULL, 'N', 'N', 'N', 'W', 540155, NULL, NULL, NULL, NULL, NULL, 'de.metas.dunning', '_Mahnungen', 'N', 'Mahnungen', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540759, 0, 0, 'Y', '2017-02-01 17:08:34+01', 100, '2017-02-01 17:08:34+01', 'Mahndisposition', 100, NULL, 'N', 'N', 'N', 'W', 540154, NULL, NULL, NULL, NULL, NULL, 'de.metas.dunning', '_Mahndisposition', 'N', 'Mahndisposition', NULL, NULL);
INSERT INTO webui_ad_menu VALUES (540760, 0, 0, 'Y', '2017-02-01 17:09:08+01', 100, '2017-02-01 17:09:08+01', 'Mahnart', 100, 'Mahnstufen verwalten', 'N', 'N', 'N', 'W', 159, NULL, NULL, NULL, NULL, NULL, 'U', '_Mahnart', 'N', 'Mahnart', NULL, NULL);


--
-- PostgreSQL database dump complete
--

