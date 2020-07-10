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
-- Name: webui_ad_message; Type: TABLE; Schema: backup; Owner: metasfresh
--

CREATE TABLE webui_ad_message (
    ad_message_id numeric(10,0),
    ad_client_id numeric(10,0),
    ad_org_id numeric(10,0),
    isactive character(1),
    created timestamp with time zone,
    createdby numeric(10,0),
    updated timestamp with time zone,
    updatedby numeric(10,0),
    value character varying(255),
    msgtext character varying(2000),
    msgtip character varying(2000),
    msgtype character(1),
    entitytype character varying(40)
);


ALTER TABLE webui_ad_message OWNER TO metasfresh;

--
-- Data for Name: webui_ad_message; Type: TABLE DATA; Schema: backup; Owner: metasfresh
--

INSERT INTO webui_ad_message VALUES (544339, 0, 0, 'Y', '2017-06-14 13:39:52+02', 100, '2017-06-19 18:23:49+02', 100, 'webui.window.dropZone.caption', 'Dateien hier ablegen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544341, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:29:35+02', 100, 'webui.window.Print.caption', 'Drucken', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544392, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-14 14:59:25+02', 100, 'webui.mainScreen.navigation.tooltip', 'Navigation', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544354, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:21:45+02', 100, 'webui.window.actions.caption', 'Aktionen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544351, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:22:07+02', 100, 'webui.window.addNew.caption', 'Neu hinzufügen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544340, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:22:21+02', 100, 'webui.window.advancedEdit.caption', 'Erweiterte Erfassung', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544349, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:22:43+02', 100, 'webui.window.batchEntry.caption', 'Massen Erfassung', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544350, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:22:55+02', 100, 'webui.window.batchEntryClose.caption', 'Massen Erfassung schliessen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544348, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:23:28+02', 100, 'webui.window.delete.caption', 'Löschen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544342, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:23:32+02', 100, 'webui.window.Delete.caption', 'Löschen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544355, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:24:13+02', 100, 'webui.window.elementToDisplay.caption', 'Element auswählen zur Anzeige der Merkmale', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544358, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:24:35+02', 100, 'webui.window.error.caption', 'Verbindung unterbrochen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544356, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:25:57+02', 100, 'webui.window.errorLoadingData.caption', 'Fehler beim laden der Daten ...', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544345, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:26:52+02', 100, 'webui.window.logOut.caption', 'Abmelden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544347, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:28:25+02', 100, 'webui.window.newTab.caption', 'In neuem Tab öffnen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544357, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:28:30+02', 100, 'webui.window.noData.caption', 'Keine Daten', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544344, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:29:40+02', 100, 'webui.window.settings.caption', 'Einstellungen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544352, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:29:50+02', 100, 'webui.window.sideList.attachments.empty', 'Kein Anhang vorhanden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544353, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:29:58+02', 100, 'webui.window.sideList.referenced.empty', 'Keine Referenzen vorhanden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544346, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-19 18:31:41+02', 100, 'webui.window.zoomInto.caption', 'Zoom', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544343, 0, 0, 'Y', '2017-06-14 13:39:53+02', 100, '2017-06-21 15:57:37+02', 100, 'webui.window.new.caption', 'Neu', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544384, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-28 17:56:46+02', 100, 'webui.login.browserError.description', 'Dein Browser wird ggf. nicht unterstützt. Bitte bei auftretenden Fehlern Chrome verwenden.', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544374, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:18:15+02', 100, 'webui.view.goTo.caption', 'Gehe zu Seite', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544381, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:16:11+02', 100, 'webui.login.password.caption', 'Passwort', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544382, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:16:20+02', 100, 'webui.login.selectRole.caption', 'Rolle auswählen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544383, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:16:23+02', 100, 'webui.login.send.caption', 'Senden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544391, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:16:33+02', 100, 'webui.mainScreen.actionMenu.tooltip', 'Aktions Menü', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544393, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:16:39+02', 100, 'webui.mainScreen.docStatus.tooltip', 'Beleg Status', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544390, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:16:57+02', 100, 'webui.mainScreen.sideList.attachments.tooltip', 'Anhänge', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544388, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:17:04+02', 100, 'webui.mainScreen.sideList.documentList.tooltip', 'Dokumenten Liste', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544389, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:17:19+02', 100, 'webui.mainScreen.sideList.referencedDocuments.tooltip', 'Referenzierte Daten', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544396, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:18:02+02', 100, 'webui.mainScreen.sideList.tooltip', 'Verzeichnis', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544395, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:18:10+02', 100, 'webui.mainScreen.userMenu.tooltip', 'Benutzer Menü', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544379, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:18:50+02', 100, 'webui.view.noItemSelected.caption', 'Keine Zeilen ausgewählt', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544376, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:18:57+02', 100, 'webui.view.selectAll.caption', 'Alle auswählen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544377, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:19:37+02', 100, 'webui.view.selectAllOnPage.caption', 'Alle Zeilen dieser Seite auswählen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544399, 0, 0, 'Y', '2017-06-19 15:09:40+02', 100, '2017-06-26 15:30:31+02', 100, 'webui.view.selectAllItems.caption', 'Wähle alle %(size)s Zeilen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544378, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-07-12 14:16:23+02', 100, 'webui.view.itemsSelected.caption', '%(size)s items selected', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544375, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:19:56+02', 100, 'webui.view.totalItems.caption', 'Zeilen gesamt', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544386, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:21:02+02', 100, 'webui.widget.clearPhoto.caption', 'Zurücksetzen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544387, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:21:34+02', 100, 'webui.widget.takeFromCamera.caption', 'Foto von Webcam', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544385, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:21:42+02', 100, 'webui.widget.uploadPhoto.caption', 'Foto hochladen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544362, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:21:52+02', 100, 'webui.window.activeFilter.caption', 'Aktiver Filter', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544372, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:22:26+02', 100, 'webui.window.allInbox.caption', 'Zeige alle', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544364, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:22:31+02', 100, 'webui.window.apply.caption', 'Anwenden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544368, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:22:35+02', 100, 'webui.window.back.caption', 'Zurück', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544367, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:23:10+02', 100, 'webui.window.browseTree.caption', 'Menübaum anzeigen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544360, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:23:23+02', 100, 'webui.window.connectionProblem.caption', 'Verbindungsproblem', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544359, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:25:42+02', 100, 'webui.window.error.description', 'Es treten Verbindungsprobleme auf. Bitte kontrolliere die Verbindung und lade die Seite neu.', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544361, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:26:02+02', 100, 'webui.window.filters.caption', 'Filter', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544369, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:26:17+02', 100, 'webui.window.inbox.caption', 'Nachrichten', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544371, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:26:31+02', 100, 'webui.window.inbox.empty', 'Die Nachrichtenbox ist leer', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544394, 0, 0, 'Y', '2017-06-14 14:59:25+02', 100, '2017-06-19 18:27:17+02', 100, 'webui.mainScreen.inbox.tooltip', 'Nachrichtenbox', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544380, 0, 0, 'Y', '2017-06-14 13:39:56+02', 100, '2017-06-19 18:28:01+02', 100, 'webui.login.login.caption', 'Nutzername', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544370, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:28:16+02', 100, 'webui.window.markAsRead.caption', 'Alles als gelesen markieren', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544363, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-19 18:28:56+02', 100, 'webui.window.noMandatory.caption', 'Pflicht Filter sind nicht ausgefüllt!', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544366, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 18:29:03+02', 100, 'webui.window.noResults.caption', 'Keine Ergebnisse', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544373, 0, 0, 'Y', '2017-06-14 13:39:55+02', 100, '2017-06-19 21:10:47+02', 100, 'webui.window.notification.caption', 'Nachricht', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544365, 0, 0, 'Y', '2017-06-14 13:39:54+02', 100, '2017-06-21 17:49:45+02', 100, 'webui.window.type.placeholder', 'Suchbegriff hier eingeben', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544420, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'webui.login.callToAction', 'Login', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544422, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-27 10:57:46+02', 100, 'webui.modal.actions.start', 'Start', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544409, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:21:11+02', 100, 'webui.window.quickActions.caption', 'Aktionen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544408, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:21:26+02', 100, 'webui.window.actions.emptyText', 'Keine Aktionen vorhanden', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544419, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-08-08 15:00:07+02', 100, 'webui.login.caption', 'Login', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544425, 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 18:18:11+02', 100, 'webui.window.openEditMode', 'Fenster anpassen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544426, 0, 0, 'Y', '2017-06-27 14:54:55+02', 100, '2017-06-27 18:18:31+02', 100, 'webui.window.closeEditMode', 'Fenster speichern', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544413, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:03:17+02', 100, 'webui.window.table.expand', 'Vergrößern', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544414, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:04:25+02', 100, 'webui.window.table.collapse', 'Verkleinern', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544412, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:08:03+02', 100, 'webui.window.filters.caption2', 'Filter nach', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544423, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:22:27+02', 100, 'webui.modal.actions.cancel', 'Abbrechen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544424, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:22:41+02', 100, 'webui.modal.actions.done', 'Bestätigen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544410, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:25:58+02', 100, 'webui.window.selectionAttributes.caption', 'Merkmale', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544411, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:26:09+02', 100, 'webui.window.selectionAttributes.callToAction', 'Zeile auswählen um Merkmale anzuzeigen.', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544421, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:54:35+02', 100, 'webui.login.error.fallback', 'Verbindungsproblem', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544416, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:57:57+02', 100, 'webui.view.error.windowName', 'Belegart', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544418, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:58:25+02', 100, 'webui.window.error.noStatus.description', 'Es treten Verbindungsprobleme auf. Bitte kontrolliere die Verbindung und lade die Seite neu.', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544417, 0, 0, 'Y', '2017-06-27 10:57:46+02', 100, '2017-06-28 17:58:54+02', 100, 'webui.window.error.noStatus.title', 'Verbindung unterbrochen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544415, 0, 0, 'Y', '2017-06-27 09:56:32+02', 100, '2017-06-28 17:59:36+02', 100, 'webui.window.sitemap.callToAction', 'Menübaum anzeigen', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544430, 0, 0, 'Y', '2017-07-06 08:01:42+02', 100, '2017-07-06 08:01:42+02', 100, 'webui.window.email.caption', 'Email', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544431, 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'webui.window.email.new', 'New message', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544432, 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'webui.window.email.to', 'To', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544433, 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'webui.window.email.topic', 'Topic', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544434, 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'webui.window.email.send', 'Send', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544435, 0, 0, 'Y', '2017-07-06 08:27:51+02', 100, '2017-07-06 08:27:51+02', 100, 'webui.window.email.addattachment', 'Add attachment', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544436, 0, 0, 'Y', '2017-07-06 08:36:13+02', 100, '2017-07-06 08:36:13+02', 100, 'webui.window.email.addemails', 'Add emails', NULL, 'I', 'de.metas.ui.web');
INSERT INTO webui_ad_message VALUES (544443, 0, 0, 'Y', '2017-07-17 09:01:58+02', 100, '2017-07-17 09:01:58+02', 100, 'webui.window.clearFilter.caption', 'Clear filter', NULL, 'I', 'de.metas.ui.web');


--
-- PostgreSQL database dump complete
--

