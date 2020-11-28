
--fix: returning v_Recordx
CREATE OR REPLACE FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate()
  RETURNS trigger AS
$BODY$
DECLARE
	v_M_Product_ID				NUMERIC(10);
	v_M_AttributeSetInstance_ID	NUMERIC(10);
	v_Date						DATE;
	v_Qty						NUMERIC;
	v_Record					RECORD;
BEGIN
	IF (TG_OP='INSERT') THEN
		v_M_Product_ID 				:= NEW.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= NEW.M_AttributeSetInstance_ID;
		v_Date 						:= NEW.MovementDate;
		v_Qty 						:= NEW.MovementQty;
		v_Record 					:= NEW;
	ELSIF (TG_OP='DELETE') THEN
		v_M_Product_ID 				:= OLD.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= OLD.M_AttributeSetInstance_ID;
		v_Date 						:= OLD.MovementDate;
		v_Qty 						:= OLD.MovementQty * -1;
		v_Record 					:= OLD;
	ELSE
		RAISE EXCEPTION 'Trigger operation not supported';
	END IF;

	-- insert the key
	-- task 09473: don't attempt to update an existing record. Just insert. That way we can rule out deadlocks.
	INSERT INTO "de.metas.fresh".X_Fresh_QtyOnHand_OnDate(M_Product_ID, M_AttributeSetInstance_ID, MovementDate, Qty) VALUES (v_M_Product_ID, v_M_AttributeSetInstance_ID, v_Date, v_Qty);

	RETURN v_Record;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate()
  OWNER TO adempiere;
COMMENT ON FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate() IS 'the function is VOLATILE because it is called from a trigger function and to avoid the error "ERROR: INSERT is not allowed in a non-volatile function';
