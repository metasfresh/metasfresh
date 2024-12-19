package de.metas.business_rule.log;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessRuleLogService
{
	@NonNull private final BusinessRuleLogRepository logRepository;
}
