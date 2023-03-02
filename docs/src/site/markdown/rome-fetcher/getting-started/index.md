## Retrieve feed with Rome Fetcher

The HttpURLFeedFetcher class does the actual HTTP request. It relies on
the FeedInfoCacheI interface which stores information about each feed
required for conditional gets. Currently there is a single
implementation of FeedInfoCacheI supplied: `HashMapFeedInfoCache`.

The basic usage of FeedFetcher is as follows:

```java
FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
FeedFetcher feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
SyndFeed feed = feedFetcher.retrieveFeed(new URL("http://blogs.sun.com/roller/rss/pat"));
System.out.println(feed);
```

Any subsequent fetches of
[http://blogs.sun.com/roller/rss/pat](http://blogs.sun.com/roller/rss/pat)
by any FeedFetcher using feedInfoCache will now only retrieve the feed
if it has changed.

FeedFetcher can be used without a cache if required. Simply create it using the 
zero-parameter constructor:

```
FeedFetcher feedFetcher = new HttpURLFeedFetcher();
```

Note that there has been considerable discussion on the rome-dev list about the 
best way to manage the creation of the feed fetcher. Currently the client code 
needs to be responsible for creating specific implementations of the FeedFetcherI interface.
