package co.com.estacionsannicolas.initializer;

import co.com.estacionsannicolas.beans.AwardBean;
import co.com.estacionsannicolas.beans.MarketingCampaignBean;
import co.com.estacionsannicolas.beans.PromotionCodeBatchRequestBean;
import co.com.estacionsannicolas.beans.UserBean;
import co.com.estacionsannicolas.entities.RoleEntity;
import co.com.estacionsannicolas.enums.DefaultMarketingCampaigns;
import co.com.estacionsannicolas.enums.UserRoleTypeEnum;
import co.com.estacionsannicolas.repositories.UserRoleRepository;
import co.com.estacionsannicolas.service.AwardService;
import co.com.estacionsannicolas.service.MarketingCampaignService;
import co.com.estacionsannicolas.service.PromotionCodeService;
import co.com.estacionsannicolas.service.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SeedDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MarketingCampaignService marketingCampaignService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private PromotionCodeService promotionCodeService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initializeUserRoles();
        createTanquearSiPagaCampaign();
        createDefaultUsers();
        createDefaultPromotionCodesForTanquearSiPaga();
    }

    private void createDefaultPromotionCodesForTanquearSiPaga() {
        MarketingCampaignBean tanquearSiPaga = marketingCampaignService.findByName(DefaultMarketingCampaigns.TANQUEAR_SI_PAGA.getName());
        if (tanquearSiPaga != null && promotionCodeService.getCountByCampaign(tanquearSiPaga) == 0) {
            PromotionCodeBatchRequestBean batchRequestInfo = new PromotionCodeBatchRequestBean();
            batchRequestInfo.setAwardPointsPercode(100);
            batchRequestInfo.setCodeLength(12);
            batchRequestInfo.setMarketingCampaign(tanquearSiPaga);
            batchRequestInfo.setNumberOfCodesToCreate(100);

            promotionCodeService.generateRandomCodes(batchRequestInfo);
        }
    }

    private void createTanquearSiPagaCampaign() {
        if (marketingCampaignService.findByName(DefaultMarketingCampaigns.TANQUEAR_SI_PAGA.getName()) == null) {
            MarketingCampaignBean tanquearSiPagaCampaign = new MarketingCampaignBean();
            tanquearSiPagaCampaign.setName(DefaultMarketingCampaigns.TANQUEAR_SI_PAGA.getName());
            tanquearSiPagaCampaign = marketingCampaignService.save(tanquearSiPagaCampaign);

            createTestAwards(tanquearSiPagaCampaign);
        }
    }

    private void createTestAwards(MarketingCampaignBean tanquearSiPagaCampaign) {
        List<MarketingCampaignBean> campaigns = new ArrayList<>();
        campaigns.add(tanquearSiPagaCampaign);

        for (int i = 1; i < 5; i++) {
            AwardBean testAward = new AwardBean();
            testAward.setCostInPoints(1000L);
            testAward.setDescription("This is a test award " + i);
            testAward.setName("Test award" + i);
            testAward.setImageLocation("path_to_image_" + i);
            testAward.setMarketingCampaigns(campaigns);
            testAward.setReference("00" + i);
            testAward.setPrice(BigDecimal.ONE);
            awardService.save(testAward);
        }
    }

    private void createDefaultUsers() {
        createDefaultAdmin();
        createDefaultCustomer();
    }

    private void createDefaultAdmin() {
        if (userService.findByUsername("admin") == null) {
            UserBean admin = new UserBean();
            admin.setUsername("admin");
            admin.setPassword("Admin01.");
            admin.setAcive(true);
            admin.setEmail("edssn_test1@gmail.com");
            admin.setFullName("Antonio Paternina");
            admin.setNationalId("123456789");
            userService.create(admin, UserRoleTypeEnum.ADMIN);
        }
    }

    private void createDefaultCustomer() {
        if (userService.findByUsername("customer") == null) {
            UserBean customer = new UserBean();
            customer.setUsername("customer");
            customer.setPassword("Admin01.");
            customer.setAcive(true);
            customer.setEmail("edssn_test2@gmail.com");
            customer.setFullName("Antonio Paternina");
            customer.setNationalId("46434648435");
            userService.create(customer, UserRoleTypeEnum.CUSTOMER);
        }
    }

    private void initializeUserRoles() {
        if (userRoleRepository.count() == 0) {
            List<RoleEntity> roles = new ArrayList<>();
            RoleEntity customerRole = new RoleEntity();
            customerRole.setType(UserRoleTypeEnum.CUSTOMER);
            roles.add(customerRole);

            RoleEntity adminRole = new RoleEntity();
            adminRole.setType(UserRoleTypeEnum.ADMIN);
            roles.add(adminRole);

            userRoleRepository.save(roles);
        }
    }
}
