package ba.etf.rma22.projekat.data.repositories

object AccountRepository {
     var acHash:String ="bd382302-7595-4f36-977c-b4f9f5cafb64"
    fun postaviHash(acHash:String):Boolean
    {
        var k= this.acHash !=null
        this.acHash =acHash
        return k
    }
    fun getHash():String
    {
        return acHash
    }
}