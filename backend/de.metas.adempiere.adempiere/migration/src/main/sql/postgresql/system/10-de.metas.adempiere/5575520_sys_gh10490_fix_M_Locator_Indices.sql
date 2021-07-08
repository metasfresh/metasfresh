
-- change and rename this index to be useful in finding locators via their coordinates
drop index if exists m_location_where;
drop index if exists m_locator_where;
CREATE INDEX m_locator_where
    ON m_locator (x, y, z, x1, m_warehouse_id);

-- creating this index to make sure that M_Location.Value is unique.
-- we need this because the inventory import feature depends on it.
-- if this fails on a given DB, then rename and/or deactivate the double locator(s)
drop index if exists m_locator_value;
CREATE UNIQUE INDEX m_locator_value ON m_locator (value, m_warehouse_id) where isactive='Y';
