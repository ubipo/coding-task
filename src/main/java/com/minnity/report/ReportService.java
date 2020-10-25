package com.minnity.report;

import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;

public class ReportService {

  //task 1: Return number of requests that were made for each company. (e.g. companyId -> requestNumber)
  public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {
    throw new NotImplementedException("TODO implement this method");
  }

  //task 2: Count and return requests per company that finished with an error HTTP response code (>=400)
  public Map<Integer, RequestLog> findRequestsWithError(List<RequestLog> requestLogs) {
    throw new NotImplementedException("TODO implement this method");
  }

  //task 3: find and print API (requests path) that on average takes the longest time to process the request.
  public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
    throw new NotImplementedException("TODO implement this method");
  }
}
