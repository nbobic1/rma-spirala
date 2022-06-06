package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("anketa/{id}/pitanja")
    suspend fun getPitanja(@Path("id")  id:Int): Response<List<Pitanje>>

    @GET("anketa/{id}")
    suspend fun getById(@Path("id")  id:Int): Response<Anketa>

    @GET("anketa/{id}/grupa")
    suspend fun getGrupaZaA(@Path("id")  id:Int): Response<List<Grupa>>

    @GET("student/{id}/anketataken")
    suspend fun getPoceteAnkete(@Path("id")id:String): Response<List<AnketaTaken>>
    @GET("grupa/{id}/ankete")
    suspend fun getAnketeGrupe(@Path("id")  id:Int): Response<List<Anketa>>

    @GET("student/{id}/anketataken/{kid}/odgovori")
    suspend fun getOdgovoriAnketa(@Path("id") id:String, @Path("kid") kid: Int): Response<List<Odgovor>>

    @GET("anketa")
    suspend fun getAll(): Response<List<Anketa>>

    @GET("anketa")
    suspend fun getAll(@Query("offset") offset:Int): Response<List<Anketa>>


    @GET("istrazivanje")
    suspend fun getIstrazivanja(@Query("offset") offset:Int): Response<List<Istrazivanje>>

    @GET("istrazivanje")
    suspend fun getIstrazivanja(): Response<List<Istrazivanje>>
    @GET("grupa")
    suspend fun getGrupe(): Response<List<Grupa>>

    @GET("student/{kid}/grupa")
    suspend fun getUpisaneGrupe(@Path("kid") kid:String): Response<List<Grupa>>

    @POST("grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu(@Path("gid") gid:Int,@Path("id") id:String): Response<Message>

    @POST("student/{id}/anketa/{kid}")
    suspend fun zapocniAnketu(@Path("id") id:String,@Path("kid") kid:Int): Response<AnketaTaken>

    @POST("student/{id}/anketataken/{kid}/odgovor")
    suspend fun postaviOdgovorAnketa(@Path("id") id:String,@Path("kid") kid:Int,@Body odgovor:Pos): Response<Odgovor>

    @GET("grupa/{idGrupa}/istrazivanje")
    suspend fun getIstrazivanjaZaGrupu(@Path("idGrupa") idGrupa: Int): Response<Istrazivanje>
}