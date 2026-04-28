drop index if exists M_Inventory_Candidate_UQ;

create unique index M_Inventory_Candidate_UQ on M_Inventory_Candidate (m_hu_id, m_product_id) where processed='N';

