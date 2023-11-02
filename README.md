# Thomas Morris's DV01 Code Assessment (Backend)

## How to run

Install sbt for:
- MacOS -> https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
- Windows -> https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html
- Linux -> https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html

Once downloaded to run the application, enter:

```shell
sbt run
```
Then call this endpoint: 

```
http://localhost:9000/loans?size=10&sort=issue_date
```
Where the query params `size` must be a number and `sort` supports the values:
`issued_date`, `loan_amount`, `grade`, `fico_low`, and `fico_high`.

### Run test

```shell
sbt test
```

### Side note

Due to the size of the csv, gitHub won't allow you to add the csv to the repository (at least on a free account).
So I'll ignore the file in .gitignore, but please download the csv from [here](https://drive.google.com/file/d/1RdRVZdy_UYknm0Qr9clXAlQIi0Pts9VI/view)
and put it in this directory: `conf/data`

## Improvements to be made

1. Pulling directly from a csv everytime that is over 100MB is pretty intensive. In addition, the filtering, sorting,
and querying all has to be performed by the backend rather than by the DB. So I'm setting up a script here: `/script/convert_csv_to_db.go`
that would be written in GoLang in order to run this script one time to move the data from the csv into a DB.
2. Given the amount of data we have in conjunction to us potentially querying per loan account, it would
ideal to use a caching storage (e.g. Redis/Memcache) to store our loan data, so we don't need to query
from the DB unless there's an write operation.
3. With more time, I would have implemented a proper controller test where I can check that
my response body is returned correctly based on the request.

