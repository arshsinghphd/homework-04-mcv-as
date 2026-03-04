# Payroll Generator Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.

If you are using mermaid markup to generate your class diagrams, you may edit this document in the sections below to insert your markup to generate each diagram. Otherwise, you may simply include the images for each diagram requested below in your zipped submission (be sure to name each diagram image clearly in this case!)

## (INITIAL DESIGN): Class Diagram

Include a UML class diagram of your initial design for this assignment. If you are using the mermaid markdown, you may include the code for it here. For a reminder on the mermaid syntax, you may go [here](https://mermaid.js.org/syntax/classDiagram.html)

```mermaid
classDiagram
direction TB
    
    class DNInfoApp {
        - DNInfoApp()
        + main(String[]) void 
    }
    
    namespace Controller {
        class ArgsController {
            + ArgsController()
            String help
        }
    }
    
    namespace Model {
        class DNRecord {
            + DNRecord(String, String, String, String, String, String, double, double)
            + region() String
            + longitude() double
            + latitude() double
            + country() String
            + hostname() String
            + postal() String
            + city() String
            + ip() String
        }
        
        class DataFormatter {
            - DataFormatter()
            - prettySingle(DNRecord, PrintStream) void
            - writeJsonData(Collection~DNRecord~, OutputStream) void
            - writeCSVData(Collection~DNRecord~, OutputStream) void
            + write(Collection~DNRecord~, Formats, OutputStream) void
            - prettyPrint(Collection~DNRecord~, OutputStream) void
            - writeXmlData(Collection~DNRecord~, OutputStream) void
        }
        
        class DomainNameModel { 
            <<Interface>>
            + writeRecords(List~DNRecord~, Formats, OutputStream) void
            + getInstance(String) DomainNameModel
            + getRecord(String) DNRecord
              DomainNameModel instance
              List~DNRecord~ records
        }
    
        class NetUtils {
            - NetUtils()
            + getIpDetails(String, Formats) InputStream
            + getApiUrl(String, Formats) String
            + lookUpIp(String) String
            + getApiUrl(String) String
            + getUrlContents(String) InputStream
            + getIpDetails(String) InputStream
        }
    
        class Formats {
            <<enumeration>>
            + Formats()
            + values() Formats[]
            + valueOf(String) Formats
            + containsValues(String) Formats?
        }

        class DomainXmlWrapper {
            + DomainXmlWrapper(Collection~DNRecord~)
        }
    }
    
    DomainNameModel  ..>  DataFormatter 
    ArgsController "1" *--> "model 1" DomainNameModel
    ArgsController "1" *--> "format 1" Formats
    DataFormatter  ..>  DNRecord
    DataFormatter  ..>  Formats
    DomainNameModel  -->  DNRecord
    DomainNameModel  ..>  Formats
    DomainXmlWrapper "1" *--> "domain *" DNRecord
    NetUtils  ..>  Formats
```


## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 
1. 
2. 

## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. We want both the diagram for your initial and final design, so you may include another image or include the finalized mermaid markup below. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
classDiagram
    direction BT
    class DNInfoApp {
        - DNInfoApp()
        + main(String[]) void
        - printHelp() void
    }

    namespace controller {
        class DNInfoController {
            + DNInfoController(DomainRepository, DomainLookupService)
            + handle(String, Format, PrintStream) void
        }
    }
    
    namespace model {
        class Domain {
            + Domain(String, String, String, String, String, String, double, double)
            + hostname() String
            + region() String
            + latitude() double
            + longitude() double
            + country() String
            + city() String
            + postal() String
            + ip() String
        }
        class DomainList {
            ~ DomainList(List~Domain~)
            + domains() List~Domain~
        }
        class DomainLookupService {
            + DomainLookupService()
            + lookup(String) Domain
        }
        class DomainNotFoundException {
            + DomainNotFoundException(String)
        }
        class DomainRepository {
            + DomainRepository(String)
            + DomainRepository()
            - String xmlFile
            + save(Domain) void
            + findByHostname(String) Domain
            - loadDomainList() DomainList?
            List~String~ allHostnames
            String xmlFile
        }
    }
    
    namespace view {
        class CSVView {
            + CSVView()
            + render(Domain, PrintStream) void
        }
        class Format {
            <<enumeration>>
            + Format()
            + valueOf(String) Format
            + values() Format[]
        }
        class IView {
            <<Interface>>
            + render(Domain, PrintStream) void
        }
        class JSONView {
            + JSONView()
            + render(Domain, PrintStream) void
        }
        class PrettyView {
            + PrettyView()
            + render(Domain, PrintStream) void
        }
        class ViewFactory {
            - ViewFactory()
            + getView(Format) IView
        }
        class XMLView {
            + XMLView()
            + render(Domain, PrintStream) void
        }
    }
    
    CSVView  ..>  IView : implements
    JSONView  ..>  IView : implements
    PrettyView  ..>  IView: implements
    XMLView  ..>  IView: implements
    ViewFactory  ..>  CSVView : uses
    ViewFactory  ..>  Format : uses
    ViewFactory  ..>  IView : uses
    ViewFactory  ..>  JSONView : uses
    ViewFactory  ..>  PrettyView : uses
    ViewFactory  ..>  XMLView : uses
    IView --> Domain: uses
    
    DomainLookupService  ..>  Domain : uses
    DomainLookupService  ..>  DomainNotFoundException : uses

    DomainList -->  Domain: uses
    DomainRepository  -->  DomainList : uses
    DomainRepository  ..>  Domain : uses
    DomainRepository  ..>  DomainList : uses
    
    DNInfoController --> DomainRepository : uses
    DNInfoController --> DomainLookupService : uses
    
    DNInfoApp --> DomainRepository : uses
    DNInfoApp --> DomainLookupService : uses
    DNInfoApp --> DNInfoController : uses
```

## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning new information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

Instead of trying to use the given files, I build all the classes from ground up. 

As it has been the case in previous assignments, I did lose some expected details on the way, especially the way DNInfoApp is expected to work. I did not account for -o, -h and --data in the first submission.

I also did not have the file structure right - namely student package layer was missing.

Overall I felt much more in control in this assignment. The few mismatches of the expectation at the end were very easy to fix since I know all the parts of this code and built them one by one test first in my personal folder. 
