--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.5
-- Dumped by pg_dump version 9.6.2

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
-- Name: webui_ad_message_trl; Type: TABLE; Schema: backup; Owner: metasfresh
--

CREATE TABLE webui_ad_message_trl (
    ad_message_id numeric(10,0),
    ad_language character varying(6),
    ad_client_id numeric(10,0),
    ad_org_id numeric(10,0),
    isactive character(1),
    created timestamp with time zone,
    createdby numeric(10,0),
    updated timestamp with time zone,
    updatedby numeric(10,0),
    msgtext character varying(2000),
    msgtip character varying(2000),
    istranslated character(1)
);


ALTER TABLE webui_ad_message_trl OWNER TO metasfresh;

--
-- Data for Name: webui_ad_message_trl; Type: TABLE DATA; Schema: backup; Owner: metasfresh
--

INSERT INTO webui_ad_message_trl VALUES (544424, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Done', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544339, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:52+02', 100, '2017-06-14 13:39:52+02', 100, 'Drop files here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544339, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:52+02', 100, '2017-06-14 13:39:52+02', 100, 'Drop files here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544339, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:52+02', 100, '2017-06-14 13:39:52+02', 100, 'Drop files here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544340, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Advanced Edit', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544340, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Advanced Edit', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544340, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Advanced Edit', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544341, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Print', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544341, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Print', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544341, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Print', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544342, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544342, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544342, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544343, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'New', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544343, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'New', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544343, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'New', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544344, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Settings', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544344, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Settings', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544344, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Settings', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544345, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Log Out', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544392, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Navigation', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544392, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Navigation', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544392, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Navigation', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544384, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Your browser might be not fully supported. Please try Chrome in case of any errors.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544384, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Your browser might be not fully supported. Please try Chrome in case of any errors.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544384, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-28 17:56:39+02', 100, 'Your browser might be not fully supported. Please try Chrome in case of any errors.
Dein Browser wird ggf. nicht unterstützt. Bitte bei auftretenden Fehlern Chrome verwenden.', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544345, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Log Out', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544345, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Log Out', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544346, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Zoom into', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544346, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Zoom into', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544346, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Zoom into', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544347, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Open in new tab', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544347, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Open in new tab', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544347, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Open in new tab', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544348, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544348, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544348, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Delete', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544349, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544349, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544349, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544350, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Close batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544350, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Close batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544350, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Close batch entry', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544351, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Add new', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544351, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Add new', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544351, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'Add new', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544352, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'There is no attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544352, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'There is no attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544352, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-14 13:39:53+02', 100, 'There is no attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544353, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There is no referenced document', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544353, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There is no referenced document', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544353, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There is no referenced document', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544354, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544354, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544354, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544355, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Select element to display its attributes', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544355, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Select element to display its attributes', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544355, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Select element to display its attributes', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544356, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Error loading data...', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544356, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Error loading data...', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544356, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Error loading data...', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544357, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'No data', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544357, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'No data', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544357, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'No data', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544358, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection lost', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544358, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection lost', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544358, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection lost', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544359, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There are some connection issues. Check connection and try to refresh the page.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544359, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There are some connection issues. Check connection and try to refresh the page.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544359, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'There are some connection issues. Check connection and try to refresh the page.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544360, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection problem', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544360, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection problem', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544360, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Connection problem', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544361, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Filters', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544361, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Filters', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544361, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Filters', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544362, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Active Filter', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544362, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Active Filter', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544362, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Active Filter', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544363, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Mandatory filters are not filled!', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544363, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Mandatory filters are not filled!', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544363, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Mandatory filters are not filled!', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544364, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Apply', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544364, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Apply', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544364, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Apply', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544365, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Type phrase here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544365, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Type phrase here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544365, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-14 13:39:54+02', 100, 'Type phrase here', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544366, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'There are no results', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544366, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'There are no results', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544366, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'There are no results', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544367, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544367, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544367, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544368, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Back', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544368, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Back', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544368, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Back', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544369, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544369, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544369, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544370, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Mark all as read', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544370, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Mark all as read', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544370, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Mark all as read', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544371, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox is empty', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544371, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox is empty', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544371, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Inbox is empty', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544372, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Show all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544372, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Show all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544372, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Show all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544373, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Notification', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544373, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Notification', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544373, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Notification', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544374, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Go to page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544374, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Go to page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544374, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Go to page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544375, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Total items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544375, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Total items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544375, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Total items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544376, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544376, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544376, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544377, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all on this page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544377, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all on this page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544377, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'Select all on this page', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544379, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'No item selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544379, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'No item selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544379, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'No item selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544380, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544380, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544380, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544381, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Password', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544381, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Password', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544381, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Password', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544382, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Select role', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544382, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Select role', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544382, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Select role', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544383, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544383, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544383, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544385, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Upload a photo', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544385, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Upload a photo', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544385, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Upload a photo', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544386, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Clear', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544386, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Clear', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544386, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Clear', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544387, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Take from camera', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544387, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Take from camera', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544387, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-14 13:39:56+02', 100, 'Take from camera', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544388, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Document list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544388, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Document list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544388, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Document list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544389, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Referenced documents', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544389, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Referenced documents', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544389, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Referenced documents', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544390, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Attachments', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544390, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Attachments', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544390, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Attachments', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544391, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Action menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544391, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Action menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544391, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Action menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544393, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Doc status', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544378, 'de_CH', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'items selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544378, 'en_US', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'items selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544378, 'nl_NL', 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-14 13:39:55+02', 100, 'items selected', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544393, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Doc status', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544393, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Doc status', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544394, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544394, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544394, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Inbox', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544395, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'User menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544395, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'User menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544395, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'User menu', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544396, 'de_CH', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Side list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544396, 'en_US', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Side list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544396, 'nl_NL', 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'Side list', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544419, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-08-08 14:59:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544399, 'de_CH', 0, 0, 'Y', '2017-06-19 15:09:40+02', 100, '2017-06-26 15:30:44+02', 100, 'Select all %(size)s items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544399, 'en_US', 0, 0, 'Y', '2017-06-19 15:09:40+02', 100, '2017-06-26 15:30:47+02', 100, 'Select all %(size)s items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544399, 'nl_NL', 0, 0, 'Y', '2017-06-19 15:09:40+02', 100, '2017-06-26 15:30:52+02', 100, 'Select all %(size)s items', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544409, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544409, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544409, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544410, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Selection attributes', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544410, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Selection attributes', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544411, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Select element to display its attributes.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544411, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Select element to display its attributes.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544412, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Filter by', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544412, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Filter by', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544412, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Filter by', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544413, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Expand', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544413, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Expand', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544413, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Expand', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544414, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Collapse', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544414, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Collapse', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544414, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Collapse', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544415, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544415, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544415, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-27 09:56:32+02', 100, 'Browse whole tree', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544416, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Document type', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544416, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Document type', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544416, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Document type', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544417, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Connection lost.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544417, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Connection lost.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544417, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Connection lost.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544418, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'There are some connection issues. Check connection and try to refresh the page.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544418, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'There are some connection issues. Check connection and try to refresh the page.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544419, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544419, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544420, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544420, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544420, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Login', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544421, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Connection problem', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544421, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Connection problem', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544422, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Start', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544422, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Start', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544422, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Start', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544423, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Cancel', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544423, 'en_US', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Cancel', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544423, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Cancel', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544424, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Done', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544408, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:21:42+02', 100, 'Keine Aktionen vorhanden', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544408, 'en_US', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:21:54+02', 100, 'There are no actions', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544408, 'nl_NL', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:22:06+02', 100, 'There are no actions', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544411, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:26:14+02', 100, 'Zeile auswählen um Merkmale anzuzeigen.', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544410, 'de_CH', 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:26:24+02', 100, 'Merkmale', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544421, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:54:47+02', 100, 'Verbindungsproblem', NULL, 'Y');
INSERT INTO webui_ad_message_trl VALUES (544418, 'de_CH', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:58:33+02', 100, 'Es treten Verbindungsprobleme auf. Bitte kontrolliere die Verbindung und lade die Seite neu.', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544424, 'nl_NL', 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'Done', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544425, 'de_CH', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Open edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544425, 'en_US', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Open edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544425, 'nl_NL', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Open edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544426, 'de_CH', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Close edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544426, 'en_US', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Close edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544426, 'nl_NL', 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 14:54:55+02', 100, 'Close edit mode', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544430, 'de_CH', 0, 0, 'Y', '2017-07-06 08:01:42+02', 100, '2017-07-06 08:01:42+02', 100, 'Email', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544430, 'en_US', 0, 0, 'Y', '2017-07-06 08:01:42+02', 100, '2017-07-06 08:01:42+02', 100, 'Email', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544430, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:01:42+02', 100, '2017-07-06 08:01:42+02', 100, 'Email', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544431, 'de_CH', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'New message', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544431, 'en_US', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'New message', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544431, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'New message', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544432, 'de_CH', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'To', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544432, 'en_US', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'To', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544432, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'To', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544433, 'de_CH', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Topic', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544433, 'en_US', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Topic', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544433, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Topic', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544434, 'de_CH', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544434, 'en_US', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544434, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Send', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544435, 'de_CH', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Add attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544435, 'en_US', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Add attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544435, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'Add attachment', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544436, 'de_CH', 0, 0, 'Y', '2017-07-06 08:36:13+02', 100, '2017-07-06 08:36:13+02', 100, 'Add emails', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544436, 'en_US', 0, 0, 'Y', '2017-07-06 08:36:13+02', 100, '2017-07-06 08:36:13+02', 100, 'Add emails', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544436, 'nl_NL', 0, 0, 'Y', '2017-07-06 08:36:13+02', 100, '2017-07-06 08:36:13+02', 100, 'Add emails', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544443, 'de_CH', 0, 0, 'Y', '2017-07-17 09:01:58+02', 100, '2017-07-17 09:01:58+02', 100, 'Clear filter', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544443, 'nl_NL', 0, 0, 'Y', '2017-07-17 09:01:58+02', 100, '2017-07-17 09:01:58+02', 100, 'Clear filter', NULL, 'N');
INSERT INTO webui_ad_message_trl VALUES (544443, 'en_US', 0, 0, 'Y', '2017-07-17 09:01:58+02', 100, '2017-07-17 09:01:58+02', 100, 'Clear filter', NULL, 'N');


--
-- PostgreSQL database dump complete
--

