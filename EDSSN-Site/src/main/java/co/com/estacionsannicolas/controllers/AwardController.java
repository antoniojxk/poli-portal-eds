package co.com.estacionsannicolas.controllers;

import co.com.estacionsannicolas.beans.AwardBean;
import co.com.estacionsannicolas.entities.RoleEntity;
import co.com.estacionsannicolas.service.AwardService;
import co.com.estacionsannicolas.service.exceptions.RequiredParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/awards")
public class AwardController extends BaseController {
    @Autowired
    private AwardService awardService;

    @Secured(RoleEntity.ADMIN)
    @RequestMapping(method = RequestMethod.GET, params = {"marketingCampaignId"})
    public List<AwardBean> getAwardsForMarketingCampaign(@RequestParam long marketingCampaignId) {
        return awardService.getAwardsForMarketingCampaign(marketingCampaignId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AwardBean> getAll() {
        return awardService.getAll();
    }

    @Secured(RoleEntity.ADMIN)
    @RequestMapping(method = RequestMethod.POST)
    public AwardBean save(@RequestBody AwardBean awardBean) throws RequiredParameterException {
        return awardService.save(awardBean);
    }

    @Secured(RoleEntity.ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AwardBean update(@RequestBody AwardBean awardBean) throws RequiredParameterException {
        return awardService.save(awardBean);
    }

    @Secured(RoleEntity.ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        awardService.delete(id);
    }
}
