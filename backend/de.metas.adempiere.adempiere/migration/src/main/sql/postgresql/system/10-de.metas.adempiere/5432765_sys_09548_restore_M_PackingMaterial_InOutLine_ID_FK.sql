

-- fix dangling references if the FK wasn't yet existing
UPDATE M_InOutLine iol 
SET M_PackingMaterial_InOutLine_ID=NULL 
WHERE 
	iol.M_PackingMaterial_InOutLine_ID IS NOT NULL
	AND NOT EXISTS (select 1 from M_InOutLine iol2 where iol2.M_InOutLine_ID=iol.M_PackingMaterial_InOutLine_ID)
;
-- ... or drop the FK it if exists
ALTER TABLE m_inoutline DROP CONSTRAINT IF EXISTS mpackingmaterialinoutline_mino;

ALTER TABLE m_inoutline
  ADD CONSTRAINT mpackingmaterialinoutline_mino FOREIGN KEY (m_packingmaterial_inoutline_id)
      REFERENCES m_inoutline (m_inoutline_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED;
