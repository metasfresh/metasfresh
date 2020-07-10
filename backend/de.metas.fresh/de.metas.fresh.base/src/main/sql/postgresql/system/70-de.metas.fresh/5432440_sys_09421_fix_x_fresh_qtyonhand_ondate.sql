

ALTER TABLE "de.metas.fresh".x_fresh_qtyonhand_ondate DROP CONSTRAINT IF EXISTS x_fresh_qtyonhand_ondate_pkey;

DROP INDEX IF eXISTS "de.metas.fresh".x_fresh_qtyonhand_ondate_tuple;

CREATE INDEX x_fresh_qtyonhand_ondate_tuple
  ON "de.metas.fresh".x_fresh_qtyonhand_ondate
  USING btree
  (movementdate , m_product_id , m_attributesetinstance_id );
