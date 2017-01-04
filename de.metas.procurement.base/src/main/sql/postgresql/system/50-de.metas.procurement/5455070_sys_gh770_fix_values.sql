
CREATE TABLE public.C_Flatrate_DataEntry_bkp_gh770 AS SELECT * FROM C_Flatrate_DataEntry;

UPDATE C_Flatrate_DataEntry
SET FlatrateAmtPerUOM=null, Updated=now(), UpdatedBy=99, 
	Note='Der Betrag wurde durch metas von 0.00 auf <leer> korrigiert. Die ursprünglichen Werte wurden in der Backup-Tabelle public.C_Flatrate_DataEntry_bkp_gh770 gesichert. Vgl. gh #770'
WHERE C_Flatrate_DataEntry_ID IN
(
SELECT C_Flatrate_DataEntry_ID 
FROM C_Flatrate_DataEntry e 
	JOIN C_Flatrate_Term t ON t.C_Flatrate_Term_ID=e.C_Flatrate_Term_ID
		JOIN C_Flatrate_Term t_prev ON t_prev.C_FlatrateTerm_Next_ID=t.C_Flatrate_Term_ID -- we only care for term that were extended because that's where the bug was
WHERE t.type_conditions='Procuremnt' -- we only care for producrement terms because that's where the bug was
	AND e.FlatrateAmtPerUOM = 0
);

UPDATE C_Flatrate_DataEntry
SET FlatrateAmt=null, Updated=now(), UpdatedBy=99, 
	Note='Der Betrag wurde durch metas von 0.00 auf <leer> korrigiert. Die ursprünglichen Werte wurden in der Backup-Tabelle public.C_Flatrate_DataEntry_bkp_gh770 gesichert. Vgl. gh #770'
WHERE C_Flatrate_DataEntry_ID IN
(
SELECT C_Flatrate_DataEntry_ID 
FROM C_Flatrate_DataEntry e 
	JOIN C_Flatrate_Term t ON t.C_Flatrate_Term_ID=e.C_Flatrate_Term_ID
		JOIN C_Flatrate_Term t_prev ON t_prev.C_FlatrateTerm_Next_ID=t.C_Flatrate_Term_ID -- we only care for term that were extended because that's where the bug was
WHERE t.type_conditions='Procuremnt' -- we only care for producrement terms because that's where the bug was
	AND e.FlatrateAmt = 0
);
