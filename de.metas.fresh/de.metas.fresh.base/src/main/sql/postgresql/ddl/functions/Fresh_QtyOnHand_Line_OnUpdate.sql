


DROP TRIGGER IF EXISTS Fresh_QtyOnHand_Line_OnUpdate_Trigger ON Fresh_QtyOnHand_Line;
DROP FUNCTION IF EXISTS "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate();

CREATE OR REPLACE FUNCTION "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate()
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
			from Fresh_QtyOnHand h where h.Fresh_QtyOnHand_ID=NEW.Fresh_QtyOnHand_ID;
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
ALTER FUNCTION "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate() OWNER TO adempiere;

CREATE TRIGGER Fresh_QtyOnHand_Line_OnUpdate_Trigger
	BEFORE INSERT OR UPDATE
	ON Fresh_QtyOnHand_Line
	FOR EACH ROW
	EXECUTE PROCEDURE "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate();

