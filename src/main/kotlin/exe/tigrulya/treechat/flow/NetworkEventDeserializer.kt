package exe.tigrulya.treechat.flow

import com.google.gson.Gson

interface Serializer {
    fun <D> toObject(bytes: ByteArray, clazz: Class<D>): D
    fun <D> toBytes(source: D): ByteArray
}

inline fun <reified D> Serializer.toObject(bytes: ByteArray): D = toObject(bytes, D::class.java)

class GsonSerializer : Serializer {
    private val gson = Gson()

    override fun <D> toObject(bytes: ByteArray, clazz: Class<D>): D = gson.fromJson(
        bytes.toString(Charsets.UTF_8),
        clazz
    )

    override fun <D> toBytes(source: D): ByteArray = gson.toJson(
        source
    ).toByteArray(Charsets.UTF_8)
}