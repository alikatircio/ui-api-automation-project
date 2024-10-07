package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ApplicationPage;
import pages.FindJob;
import utils.Config;



public class FindJobTest extends BaseTest {

    public enum JobTitle {
        QUALITY_ASSURANCE("Quality Assurance");

        private final String title;

        JobTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public enum JobLocation {
        ISTANBUL("Istanbul, Turkey");

        private final String location;

        JobLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }
    }
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testFindJob(){

        driver.get(Config.getProperty("url")+"/careers/quality-assurance/");
        logger.info("Insider career quality assurance sayfasına gidildi.");

        FindJob findJob = new FindJob(driver);
        findJob.seeAllJobs();
        findJob.selectLocationAndDepartment(JobLocation.ISTANBUL.getLocation());
        Assert.assertTrue(findJob.isJobListLoaded(), "İş ilanı bulunamadı");
        Assert.assertTrue(findJob.verifyJobDetails(JobTitle.QUALITY_ASSURANCE.getTitle(),JobLocation.ISTANBUL.getLocation()), "İş ilanı detayları doğrulanamadı.");
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.viewRoleOnApplicationPage(findJob.jobTitle, JobTitle.QUALITY_ASSURANCE.getTitle(),JobLocation.ISTANBUL.getLocation());
    }
}
