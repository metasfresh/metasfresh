DROP TRIGGER IF EXISTS fresh_QtyOnHand_Line_OnUpdate_Trigger ON fresh_QtyOnHand_Line;
DROP FUNCTION IF EXISTS "de.metas.fresh".fresh_QtyOnHand_Line_OnUpdate();

CREATE OR REPLACE FUNCTION "de.metas.fresh".fresh_QtyOnHand_Line_OnUpdate()
  RETURNS trigger AS
$BODY$
DECLARE
	v_DateDoc DATE;
BEGIN
	--
	-- Update DateDoc from header
	-- NOTE: we are updating it only if it's null because else, the Java BL will take care
	if (NEW.DateDoc IS NULL) then
		select h.DateDoc
			into NEW.DateDoc
			from fresh_QtyOnHand h where h.fresh_QtyOnHand_ID=NEW.fresh_QtyOnHand_ID;
		--
		NEW.DateDoc := v_DateDoc;
	end if;
	
	--
	-- Update ASIKey
	if (NEW.M_AttributeSetInstance_ID is not null)
	then
		NEW.ASIKey := COALESCE(generateHUStorageASIKey(NEW.M_AttributeSetInstance_ID), '-');
	else
		NEW.ASIKey := '-';
	end if;
	
	return NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "de.metas.fresh".fresh_QtyOnHand_Line_OnUpdate() OWNER TO adempiere;


CREATE TRIGGER fresh_QtyOnHand_Line_OnUpdate_Trigger
	BEFORE INSERT OR UPDATE
	ON fresh_QtyOnHand_Line
	FOR EACH ROW
	EXECUTE PROCEDURE "de.metas.fresh".fresh_QtyOnHand_Line_OnUpdate();

