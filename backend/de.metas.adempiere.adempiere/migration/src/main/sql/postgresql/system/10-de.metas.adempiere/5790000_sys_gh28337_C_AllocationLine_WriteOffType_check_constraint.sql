-- gh#28337: Add check constraint for WriteOffType column on C_AllocationLine
ALTER TABLE C_AllocationLine DROP CONSTRAINT IF EXISTS WriteOffType_Check;
ALTER TABLE C_AllocationLine ADD CONSTRAINT WriteOffType_Check CHECK (WriteOffType IN ('WO', 'BF'));
