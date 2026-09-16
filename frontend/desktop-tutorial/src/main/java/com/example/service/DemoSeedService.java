package com.example.service;

import com.example.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DemoSeedService {
    private final FarmRepository farmRepository;
    private final FieldRepository fieldRepository;
    private final AlertMessageRepository alertMessageRepository;
    private final DiagnosisFeedbackRepository diagnosisFeedbackRepository;
    private final MonitoringPlanRepository monitoringPlanRepository;
    private final ExpertEscalationRepository expertEscalationRepository;
    private final ReferralRepository referralRepository;
    private final HotspotRepository hotspotRepository;
    private final TrapReadingRepository trapReadingRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final GovernanceConsentRepository governanceConsentRepository;

    public DemoSeedService(FarmRepository farmRepository, FieldRepository fieldRepository,
                          AlertMessageRepository alertMessageRepository,
                          DiagnosisFeedbackRepository diagnosisFeedbackRepository,
                          MonitoringPlanRepository monitoringPlanRepository,
                          ExpertEscalationRepository expertEscalationRepository,
                          ReferralRepository referralRepository,
                          HotspotRepository hotspotRepository,
                          TrapReadingRepository trapReadingRepository,
                          SensorReadingRepository sensorReadingRepository,
                          GovernanceConsentRepository governanceConsentRepository) {
        this.farmRepository = farmRepository;
        this.fieldRepository = fieldRepository;
        this.alertMessageRepository = alertMessageRepository;
        this.diagnosisFeedbackRepository = diagnosisFeedbackRepository;
        this.monitoringPlanRepository = monitoringPlanRepository;
        this.expertEscalationRepository = expertEscalationRepository;
        this.referralRepository = referralRepository;
        this.hotspotRepository = hotspotRepository;
        this.trapReadingRepository = trapReadingRepository;
        this.sensorReadingRepository = sensorReadingRepository;
        this.governanceConsentRepository = governanceConsentRepository;
    }

    public int seedDemoData() {
        int rows = 0;
        rows += farmRepository.count();
        rows += fieldRepository.count();
        rows += alertMessageRepository.count();
        rows += diagnosisFeedbackRepository.count();
        rows += monitoringPlanRepository.count();
        rows += expertEscalationRepository.count();
        rows += referralRepository.count();
        rows += hotspotRepository.count();
        rows += trapReadingRepository.count();
        rows += sensorReadingRepository.count();
        rows += governanceConsentRepository.count();
        return rows;
    }
}
