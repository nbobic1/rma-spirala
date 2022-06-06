package ba.etf.rma22.projekat.data.repositories


object ApiConfig {
        var baseURL: String = "https://rma22ws.herokuapp.com"
    fun postaviBaseURL(url:String):Unit
    {
        baseURL=url
    }
}