package com.minnity.report;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

  //task 1: Return number of requests that were made for each company. (e.g. companyId -> requestNumber)
  public Map<Integer, Long> calculateNumberOfRequestsPerCompany(List<RequestLog> requestLogs) {
    return requestLogs.stream()
      .collect(Collectors.groupingBy(RequestLog::getCompanyId, Collectors.counting()));
  }

  //task 2: Count and return requests per company that finished with an error HTTP response code (>=400)
  /* I do not quite understand this method's return type...
  `Integer` represents a company id, but what does RequestLog represent?
  The first log with an error? The last log? Is there only one log with an error per company?

  I added a different version of this method below (and postfixed this one with old). */
  public Map<Integer, RequestLog> findRequestsWithErrorOld(List<RequestLog> requestLogs) {
    throw new NotImplementedException("TODO implement this method");
  }

  // task 2 changed
  public Map<Integer, Long> findRequestsWithError(List<RequestLog> requestLogs) {
    return requestLogs.stream()
      .filter(log -> log.getRequestStatus() >= 400)
      .collect(Collectors.groupingBy(RequestLog::getCompanyId, Collectors.counting()));
  }

  //task 3: find and print API (requests path) that on average takes the longest time to process the request.
  /* Not printed */
  public String findRequestPathWithLongestDurationTime(List<RequestLog> requestLogs) {
    return findRequestPathAverageDurationTime(requestLogs)
      .entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue))
      .map(Map.Entry::getKey).orElse(null);
  }

  private Map<String, Double> findRequestPathAverageDurationTime(List<RequestLog> requestLogs) {
    return requestLogs.stream()
      .collect(Collectors.groupingBy(
              RequestLog::getRequestPath,
              Collectors.averagingLong(RequestLog::getRequestDuration)
      ));
  }
}
