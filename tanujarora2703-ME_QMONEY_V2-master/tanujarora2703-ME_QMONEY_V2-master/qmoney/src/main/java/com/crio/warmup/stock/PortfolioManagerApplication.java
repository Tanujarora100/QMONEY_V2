
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.logging.log4j.ThreadContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  // Task:
  // - Read the json file provided in the argument[0], The file is available in the classpath.
  // - Go through all of the trades in the given file,
  // - Prepare the list of all symbols a portfolio has.
  // - if "trades.json" has trades like
  // [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  // Then you should return ["MSFT", "AAPL", "GOOGL"]
  // Hints:
  // 1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  // Check if they are of any help to you.
  // 2. Return the list of all symbols in the same order as provided in json.

  // Note:
  // 1. There can be few unused imports, you will need to fix them to make the build pass.
  // 2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    String fileName = args[0];
    List<String> symbols = new ArrayList<>();
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] trades =
        objectMapper.readValue(resolveFileFromResources(fileName), PortfolioTrade[].class);
    for (PortfolioTrade trade : trades) {
      symbols.add(trade.getSymbol());
    }
    return symbols;
  }



  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  // Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  // for the stocks provided in the Json.
  // Use the function you just wrote #calculateAnnualizedReturns.
  // Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.



  // Find out the closing price of each stock on the end_date and return the list
  // of all symbols in ascending order by its close value on end date.

  // >>>>>>> c87a54d237c70b3735f29a1cfe27cd6e6986c2db
  // Note:
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  // and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(Thread.currentThread().getContextClassLoader().getResource(filename).toURI())
        .toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  // Follow the instructions provided in the task documentation and fill up the correct values for
  // the variables provided. First value is provided for your reference.
  // A. Put a breakpoint on the first line inside mainReadFile() which says
  // return Collections.emptyList();
  // B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  // following the instructions to run the test.
  // Once you are able to run the test, perform following tasks and record the output as a
  // String in the function below.
  // Use this link to see how to evaluate expressions -
  // https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  // 1. evaluate the value of "args[0]" and set the value
  // to the variable named valueOfArgument0 (This is implemented for your reference.)
  // 2. In the same window, evaluate the value of expression below and set it
  // to resultOfResolveFilePathArgs0
  // expression ==> resolveFileFromResources(args[0])
  // 3. In the same window, evaluate the value of expression below and set it
  // to toStringOfObjectMapper.
  // You might see some garbage numbers in the output. Dont worry, its expected.
  // expression ==> getObjectMapper().toString()
  // 4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  // second place from top to variable functionNameFromTestFileInStackTrace
  // 5. In the same window, you will see the line number of the function in the stack trace window.
  // assign the same to lineNumberFromTestFileInStackTrace
  // Once you are done with above, just run the corresponding test and
  // make sure its working as expected. use below command to do the same.
  // ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

    String valueOfArgument0 = "trades.json";
    String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/tanujarora2703-ME_QMONEY_V2/qmoney/src/main/resources/trades.json";
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@6d7fc27";
    String functionNameFromTestFileInStackTrace = "mainReadFile";
    String lineNumberFromTestFileInStackTrace = "29";


    return Arrays.asList(
        new String[] {valueOfArgument0, resultOfResolveFilePathArgs0, toStringOfObjectMapper,
            functionNameFromTestFileInStackTrace, lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    ObjectMapper objectMapper = getObjectMapper();
    List<PortfolioTrade> trades = Arrays
        .asList(objectMapper.readValue(resolveFileFromResources(args[0]), PortfolioTrade[].class));
    List<TotalReturnsDto> sortedByValue = mainReadQuotesHelper(args, trades);
    Collections.sort(sortedByValue, TotalReturnsDto.closingComparator);
    List<String> stocks = new ArrayList<>();
    for (TotalReturnsDto trd : sortedByValue)
      stocks.add(trd.getSymbol());
    return stocks;
  }

  public static List<TotalReturnsDto> mainReadQuotesHelper(String[] args,
      List<PortfolioTrade> trades) throws IOException, URISyntaxException {
    RestTemplate RestTemplate = new RestTemplate();
    List<TotalReturnsDto> tests = new ArrayList<TotalReturnsDto>();
    for (PortfolioTrade t : trades) {
      String uri = "https://api.tiingo.com/tiingo/daily/" + t.getSymbol() + "/prices?startDate="
          + t.getPurchaseDate().toString() + "&endDate=" + args[1]
          + "&token=3e00425b9305e10361e84c5ab3a4f4b3b17cf9ca";
      TiingoCandle[] results = RestTemplate.getForObject(uri, TiingoCandle[].class);
      if (results != null) {
        tests.add(new TotalReturnsDto(t.getSymbol(), results[results.length - 1].getClose()));
      }
    }
    return tests;
  }

  // TODO:
  // After refactor, make sure that the tests pass by using these two commands
  // ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  // ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename)
      throws IOException, URISyntaxException {

    List<PortfolioTrade> portfolioTrades = getObjectMapper().readValue(
        resolveFileFromResources(filename), new TypeReference<List<PortfolioTrade>>() {});
    return portfolioTrades;
  }


  // TODO:
  // Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    return "https://api.tiingo.com/tiingo/daily/" + trade.getSymbol() + "/prices?startDate="
        + trade.getPurchaseDate() + "&endDate=" + endDate + "&token=" + token;
  }

  // TODO:
  // Ensure all tests are passing using below command
  // ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) throws JsonProcessingException {
    ObjectMapper mapper = getObjectMapper();
    String candlesJson = mapper.writeValueAsString(candles);
    TiingoCandle firstCandle =
        mapper.readValue(candlesJson, new TypeReference<List<TiingoCandle>>() {}).get(0);
    return firstCandle.getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles)
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper mapper = getObjectMapper();
    String candlesJson = mapper.writeValueAsString(candles);
    TiingoCandle lastCandle = mapper
        .readValue(candlesJson, new TypeReference<List<TiingoCandle>>() {}).get(candles.size() - 1);
    return lastCandle.getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    RestTemplate template = new RestTemplate();
    String url = prepareUrl(trade, endDate, token);
    TiingoCandle[] candles = template.getForObject(url, TiingoCandle[].class);

    return Arrays.stream(candles).collect(Collectors.toList());


  }



  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
        List<PortfolioTrade> portfolioTrades = readTradesFromJson(args[0]);
        List<AnnualizedReturn> annualizedReturns = new ArrayList<>();
        LocalDate localDate = LocalDate.parse(args[1]);
        for (PortfolioTrade portfolioTrade : portfolioTrades) {
          List<Candle> candles = fetchCandles(portfolioTrade, localDate, getToken());
          AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(localDate, portfolioTrade,
              getOpeningPriceOnStartDate(candles), getClosingPriceOnEndDate(candles));
          annualizedReturns.add(annualizedReturn);
        }
        return annualizedReturns.stream()
            .sorted((a1, a2) -> Double.compare(a2.getAnnualizedReturn(), a1.getAnnualizedReturn()))
            .collect(Collectors.toList());
      }
  



  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  // Return the populated list of AnnualizedReturn for all stocks.
  // Annualized returns should be calculated in two steps:
  // 1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  // 1.1 Store the same as totalReturns
  // 2. Calculate extrapolated annualized returns by scaling the same in years span.
  // The formula is:
  // annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  // 2.1 Store the same as annualized_returns
  // Test the same using below specified command. The build should be successful.
  // ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate, PortfolioTrade trade,
      Double buyPrice, Double sellPrice) {
    double totalReturn = (sellPrice - buyPrice) / buyPrice;
    double daysBetween = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
    double yearsGap = daysBetween / 365;
    double annualizedReturn = Math.pow((1 + totalReturn), (1 / yearsGap)) - 1;
    return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturn);
  }

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
        String file = args[0];
        LocalDate endDate = LocalDate.parse(args[1]);
        String contents = readFileAsString(file);
        ObjectMapper objectMapper = getObjectMapper();
        PortfolioManager portfolioManager =
            PortfolioManagerFactory.getPortfolioManager(new RestTemplate());
        List<PortfolioTrade> portfolioTrades =
            objectMapper.readValue(contents, new TypeReference<List<PortfolioTrade>>() {});
        return portfolioManager.calculateAnnualizedReturn(portfolioTrades, endDate);
  }


  private static String readFileAsString(String file) throws IOException {
    return new String(Files.readAllBytes(Paths.get(file)));
  }






















  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());
    // printJsonObject(mainReadFile(args));
    // printJsonObject(mainReadQuotes(args));
    // printJsonObject(mainCalculateSingleReturn(args)); 
    ObjectMapper objectMapper= getObjectMapper();
    System.out.println(objectMapper.toString());

  }

  public static String getToken() {
    return "3e00425b9305e10361e84c5ab3a4f4b3b17cf9ca";




  }
}

