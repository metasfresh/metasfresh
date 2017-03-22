
--delete explicit relation type "Auftragskand.<=>Auftrag (Positionsebene)"
DELETE FROM AD_Relation WHERE AD_RelationType_ID=540007;
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540007;
