# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts.

## Technical Questions

1. Describe the purpose of a model in the MVC architecture. What is the model responsible for? What are some examples of what might be included in a model?
   * Model holds the data and logic that is the engine of the task. 
   * It is responsible for making sure application is always in a valid state.

2. Describe the purpose of a controller in the MVC architecture. What is the controller responsible for? What are some examples of what might be included in a controller?
   * Controller is the middleman. It validates and handles user input. Passes the user's request to the Model. After model returns (or if validation fails), decides what View should show.

4. Describe the word serialization, and how it relates to 'data-binding' in Jackson. What is the purpose of serialization? What is the purpose of data-binding? What is the relationship between the two?
    * Serialization is the process of converting an object or data structure into a format that can be easily stored or transmitted, and then reconstructed later.
    * In the context of serialization, data binding refers to the mapping between serialized data (like JSON or XML) and objects - "binding" or forcing the data to the structure of our classes/objects.

4. Describe the differences between JSON and CSV - make sure to reference flat or hierarchical data in your answer. What are some advantages of JSON over CSV? What are some disadvantages?
  * CSV is a flat format — data is organized in rows and columns, like a spreadsheet. Every row has the same set of fields, and there's no natural way to express relationships or nesting.
  * JSON is hierarchical — data can be nested, meaning an object can contain other objects, arrays, or arrays of objects. This makes it much better suited for representing real-world entities that have complex relationships. 
  * Both CSV and JSON save data in index or row-by-row format. Each represents advantages and disadvantages as discussed below.
  * CSV are simple but rigid by design. Their simple data structure makes it easy and fast to parse them. These are easy to inspect visually. Easy to compress and decompress. Do not compress much (using bzip2 like compression algorithms), but are lean to begin with. CSV is used to represent data where relationships are not important.
  * JSON in comparison are as complex as the data they are storing. JSON saves each data with its field name, and  distinguishes between strings, numbers, booleans, and nulls. Data is not easy to interpret visually in even moderately complex cases. Parsers need to interpret the structure, and there are a lot of repeated filed names, so the files are not as fast to parse. The extra structure and repetition adds overhead. This means more bulky memory-consuming files. JSON do compress more though, and after compression they take about the same space as CSV with similar data.

5. Why would we want to use InputStreams and OutputStreams throughout a program instead of specific types of streams (for most cases)?
   * This is an application of the SOLID principles of design. We would like to design with the more abstract InputStream and OutputStream rather than more specific streams to make sure we follow the Dependency-Inversion and Open/Close principles.

6. What is the difference between a record and a class in Java? When might you use one over the other?
   * A record is a one line code equivalent of writing a Class with all getter methods and for each parameter and hashCode, equals, toString methods as well.
   * records are accessed using their (invisible) getter methods that do not have 'get' in them. 
   ```java
   public record User(String firstName, String lastName) {}
   User user = new User("Amandeep", "Singh");
   User.firstName(); // not getName()
   ```
   * The only catch is that all fields in a record are final and immutable. Thus, one cannot use records:
     * if one needs to be modify any of the fields later on.
     * if we want the methods to be inherited or extended or overridden.
   * In this way records do away with many benefits of object oriented programming for string encapsulation. The classes are final class. They cannot be inherited, extended, or their method overridden - no abstraction, inheritance, or polymorphism.
   * But then, often by design, we need some classes to not be inherited, extended or their methods overridden, or their fields modified, for example when we are reading from databases. In these cases records are a perfect hassle-free solution.

7. What is a java "bean"?
   * These are classes that are written with all fields private, but all setField and getField methods public; and a no-arguments constructor. In this way, beans are the antithesis of records - records have final fields, so no setters.
   * Classes that are designed to follow these guidelines/conventions are easy to be used in frameworks such as Jackson.

## Deeper Thinking

The data for this assignment was downloaded from ipapi.co, and the data itself is publicly listed. For the pervious assignment, we used data from Board Game Geek, which a person has a unofficial collection of the BGArena games. It is also worth noting it is actually out of date, since BGGeek has added a category for Digital Implementations of games.

Data of many types are  often available online (Here is a list of a bunch of [free API](https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/)s), and even the owners of Board Game Geek have RPGGeek and VideoGameGeek. To try out Board Game geeks API, you can put into the url https://www.boardgamegeek.com/xmlapi2/thing?id=13 and you will get back an XML file with information about the game with id 13. 

Take some time to find other online data APIs that you might be interested in. What kind of data would you like to work with? What kind of data would be useful for you to have access to? Another example of an API a random dog image API https://dog.ceo/dog-api/!

🔥 Find at least two other APIs/Data sources (so downloadable data is also valid). Link them, and provide a brief description of what kind of data they provide. These will act as your references for this assignment.

1. IEEE has an API for downloading and mining the data of their publications. 
   * https://developer.ieee.org/docs/read/Metadata_API_responses
   * It returns information for various fields about the published papers requested such as name of authors, publication dates, area of research, etc.
2. In the past I have used fine grain international trade data from UN Comtrade's API. I have used it to make an application that visualizes the risk of forced labor or child labor in the supply chain.
   * https://comtradeplus.un.org
   * The data they provide can have information of import and export quantities, amounts aggregated by year, country, level of finishing of the commodity (cotton, cotton-weaved, cotton-cloth, cotton-t-shirts, etc.).

However, just because information is freely available online, it does not mean

* You have legal rights to that information
* The information was collected ethically
* The information is accurate
* The information is without bias
  
🔥 Take some time to think about the ethical implications of using data from the internet. What are some ways that you can ensure that the data you are using is accurate and unbiased? Provide some examples of key questions you might ask yourself before using data in a project, and what are some questions you can use to help you evaluate the data you are using. Please include references if you use any, as there are plenty of articles out there talking about this topic.

---

### Response: Ethical Implications of Using Data from the Internet

Before using any dataset, a number of critical questions should be asked. These include where the data came from, who collected it, who is represented in the data and who is not, whether its use complies with relevant laws, and most importantly whether using it could cause harm to any individuals or groups.

The internet contains vast amounts of data, but collecting and using it raises serious ethical questions. Internet data reflects the people who created it, who are not a representative sample of humanity — certain demographics are over or under-represented, meaning bias is almost always present [2]. People who post on social media, for example, may not have consented to their data being scraped and used in a machine learning model. There's the question of informed consent whether using it respects the privacy and dignity of the people it represents. 

Just because data is publicly accessible doesn't mean it's ethical to use. It is important to understand how the data was collected. Self-selected data such as online reviews or surveys is almost always biased compared to a random sample. Scraped data may be protected by terms of service or intellectual property or privacy laws such as GDPR — General Data Protection Regulation, CCPA — California Consumer Privacy Act, or similar laws. Well-maintained datasets come with a datasheet or methodology explaining how the data was gathered, cleaned, and what its limitations are[1].

When evaluating the data itself the way the data was labelled could introduce bias. It is critical to think whether it is complete or has significant gaps; whether there are obvious errors or outliers suggesting quality issues. It is important to test whether the data is balanced across relevant categories such as age, gender, and geography. One could check it has been used and validated by others in the field, what its known limitations are. It is worth asking whether the dataset fairly represents the population it claims to describe and who might be missing from it. Outdated data can be just as misleading as inaccurate data[2].

Models or systems trained on biased or toxic internet data can cause real harm to real people.

[1] Gebru, T., Morgenstern, J., Vecchione, B., Vaughan, J. W., Wallach, H., Daumé III, H., & Crawford, K. (2021). Datasheets for datasets. Communications of the ACM, 64, 86–92. Accessed at arXiv: https://arxiv.org/pdf/1803.09010v8 on 24 Feb, 2026.

[2] C. Jones, "Representations and Privacy: Methodological Questions for Social Media Data," in Proc. 41st ACM Int. Conf. Design of Communication (SIGDOC '23), 2023, Access provided by Northeastern University Libray at https://dl-acm-org.ezproxy.neu.edu/doi/pdf/10.1145/3615335.3623017.

