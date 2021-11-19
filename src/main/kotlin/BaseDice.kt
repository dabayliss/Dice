class BaseDice(val Atk: Int,val Succ: Int, val Crit:Int) {
    class Result(val Fail: Byte, val Succ: Byte,val Crit: Byte, val Prob: Number) {
        fun Key() : Int {
            return (Crit.toInt() shl 16) + (Succ.toInt() shl 8) + Fail.toInt()
        }
    }
    fun Roll(): List<Result> {
        val lpos = Combinations()
        return lpos
            .map { Grade(it) }
            .groupBy { it.Key() }
            .map { Result(it.value[0].Fail,it.value[0].Succ,it.value[0].Crit,it.value.stream().mapToInt({ it.Prob.toInt() }).sum()) }
            .sortedWith(compareBy<Result>({it.Crit},{it.Succ}).reversed())
    }

    class RollCombination(val Nums: ByteArray,val Times: Number)

    fun Combinations(): List<RollCombination> {
        val res = mutableListOf<RollCombination>()
        findCombinations(res,0,ByteArray(Atk),1,0)
        return res
    }

    fun Grade(combo: RollCombination) : Result {
        var fail = 0
        var succ = 0
        var crit = 0
        for ( i in 0..combo.Nums.size-1 ) {
            val tdice = combo.Nums[i].toInt()
            if ( tdice >= Succ ) {
                if ( tdice < Crit )
                    succ++
                else
                    crit++
            } else
                fail++
        }
        return Result(fail.toByte(),succ.toByte(),crit.toByte(),combo.Times)
    }

    private fun findCombinations(res: MutableList<RollCombination>, possToFill: Int, soFar: ByteArray, mult: Int, nsf: Int) {
        if ( possToFill == soFar.size ) {
            res.add(RollCombination(soFar.clone(),mult))
        } else {
            val top = if ( possToFill == 0 ) 6 else soFar[possToFill-1]
            val omult = mult * (Atk-possToFill)
            for ( i in 1 .. top ) {
                soFar[possToFill] = i.toByte()
                if ( possToFill > 0 && i.toByte() == soFar[possToFill-1])
                    findCombinations(res,possToFill+1,soFar,omult/(nsf+1),nsf+1)
                else
                    findCombinations(res,possToFill+1,soFar,omult,1)
            }
        }
    }
}