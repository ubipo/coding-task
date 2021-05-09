package com.minnity.report;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;

public class ReportServiceTest {
  private static final int NBRO_SAMPLE_REQUESTS = 15;
  private static final int COMPANY1_ID = 1;
  private ReportService reportService;

  @Before
  public void beforeEach() {
    reportService = new ReportService();
  }

  @Test
  public void calculateNumberOfRequestsPerCompany() {
    var nbroRequestCompany1 = Long.valueOf(10);
    var requests = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withCompanyId(nextInt(2, 10)).build()
    ).limit(NBRO_SAMPLE_REQUESTS).collect(Collectors.toList());
    var requestsCompany1 = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withCompanyId(COMPANY1_ID).build()
    ).limit(nbroRequestCompany1).collect(Collectors.toList());
    requests.addAll(requestsCompany1);

    var numberOfRequestsPerCompany = reportService.calculateNumberOfRequestsPerCompany(requests);
    var actualNbroRequestCompany1 = numberOfRequestsPerCompany.get(COMPANY1_ID);
    assertEquals(nbroRequestCompany1, actualNbroRequestCompany1);
  }

  @Test
  public void findRequestsWithError() {
    var nbroRequestWithError = Long.valueOf(10);
    var requests = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withCompanyId(nextInt(2, 10)).build()
    ).limit(NBRO_SAMPLE_REQUESTS).collect(Collectors.toList());
    var company1RequestsWithoutError = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withCompanyId(COMPANY1_ID).withRequestStatus(200).build()
    ).limit(NBRO_SAMPLE_REQUESTS).collect(Collectors.toList());
    var company1RequestsWithError = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withCompanyId(COMPANY1_ID).withRequestStatus(500).build()
    ).limit(nbroRequestWithError).collect(Collectors.toList());
    requests.addAll(company1RequestsWithoutError);
    requests.addAll(company1RequestsWithError);

    var numberOfErrorsPerCompany = reportService.findRequestsWithError(requests);
    var actualNbroRequestWithError = numberOfErrorsPerCompany.get(COMPANY1_ID);
    assertEquals(nbroRequestWithError, actualNbroRequestWithError);
  }

  @Test
  public void findRequestPathWithLongestDurationTime() {
    var longPath = "/b";
    var requests = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withRequestPath("/a").withRequestDuration(10).build()
    ).limit(NBRO_SAMPLE_REQUESTS).collect(Collectors.toList());
    var longDurationRequests = Stream.generate(
      () -> SampleDataGenerator.aRequestLogBuilder().withRequestPath(longPath).withRequestDuration(20).build()
    ).limit(NBRO_SAMPLE_REQUESTS).collect(Collectors.toList());
    requests.addAll(longDurationRequests);

    var pathWithLongestDuration = reportService.findRequestPathWithLongestDurationTime(requests);
    assertEquals(pathWithLongestDuration, longPath);
  }
}
