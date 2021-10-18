object ListDictionary : IDictionary {
    private val words = mutableListOf<String>()
    private val name = "English Dictionary"

    fun getName ():String{
        return this.name;
    }

    override fun add(word: String): Boolean {
        return words.add(word)
    }

    override fun find(word: String): Boolean {
       var index = -1
        index = words.indexOf(word)
        return index >= 0
    }

    override fun size(): Int {
        return words.size
    }
}

fun main(){
    println(ListDictionary.getName())
    ListDictionary.add("Kecske")
    ListDictionary.add("Macska")
    ListDictionary.add("Kutya")
    println(ListDictionary.find("Kecske"))
    println(ListDictionary.size())

}
