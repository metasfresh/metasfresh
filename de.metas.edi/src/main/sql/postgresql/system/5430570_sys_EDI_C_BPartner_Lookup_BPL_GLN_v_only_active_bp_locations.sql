-- View: EDI_C_BPartner_Lookup_BPL_GLN_v

DROP VIEW IF EXISTS EDI_C_BPartner_Lookup_BPL_GLN_v;

CREATE OR REPLACE VIEW EDI_C_BPartner_Lookup_BPL_GLN_v AS
SELECT 
	*
FROM
(
	SELECT
		bp.C_BPartner_ID,
		bp.IsActive,
		--
		-- Note: The GLN is unique per BPL.
		-- We're just filtering by two locations here and will add them to the whereclause in the EXP_Format_Line filter.
		--
		l_main.GLN AS GLN, -- The Selector's GLN
		l_store.GLN AS StoreGLN -- The Store's GLN
	FROM C_BPartner bp
		-- Many-to-many
		LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID=bp.C_BPartner_ID 
						AND l_main.IsActive='Y'
		LEFT JOIN C_BPartner_Location l_store ON l_store.C_BPartner_ID=bp.C_BPartner_ID
						AND l_store.IsActive='Y'
						AND l_store.GLN IS NOT NULL 
						AND TRIM(BOTH ' ' FROM l_store.GLN) != ''
						AND l_store.IsShipTo='Y' --- without this, the case of two partners sharing the same location-GLN still wouldn't work
	
	-- support the case of an empty StoreNumber
	UNION 
	SELECT
		bp.C_BPartner_ID,
		bp.IsActive,
		l_main.GLN AS GLN, -- The Selector's GLN
		NULL AS StoreGLN -- The Store's GLN
	FROM C_BPartner bp
		-- Many-to-many with NULL StoreGLN
		LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID=bp.C_BPartner_ID
						AND l_main.IsActive='Y'
) master
WHERE 
	master.GLN IS NOT NULL 
	AND TRIM(BOTH ' ' FROM master.GLN) != ''
GROUP BY 
	C_BPartner_ID, IsActive, GLN, StoreGLN
;
