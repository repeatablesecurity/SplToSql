
Command line tool to translate Splunk queries into SQL queries.

Example -
Below is a Splunk Query :
    index=__your_sysmon_index__ (EventCode=12 OR EventCode=13 OR EventCode=14) TargetObject="*\\Software\\Classes\\CLSID\\*"

It will be converted into a SQL query like this :
    SELECT * FROM __your_sysmon_index__ WHERE (EventCode=12 OR EventCode=14 OR EventCode=13) AND TargetObject LIKE "*\\Environment*UserInitMprLogonScript"


To use this tool from command line, pass the Splunk query in a file like this -
    # echo 'index=__your_sysmon_index__ (EventCode=12 OR EventCode=13 OR EventCode=14) TargetObject="*\\Software\\Classes\\CLSID\\*"' > /tmp/sample
    # java -jar target/translator-1.0-SNAPSHOT.jar "/tmp/sample"

The output is printed on the stdout.
