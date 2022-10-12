### Set Up Local MongoDB

#### After Installing Brew:
```
brew install mongodb-community
```

#### To Start MongoDb
```
  brew services start mongodb/brew/mongodb-community
```

#### To Stop Mongo DB

```
  brew services stop mongodb/brew/mongodb-community
```

#### To Acess MongoDB
```
mongosh
```

#### To Show DBS

```
show dbs
```

#### To use a DB

```
use <YOUR DB NAME>
```


#### To list collections
```
show collections
````

#### To drop collection
```
db.<COLLECTION NAME>.drop()
```


