class BaseDice(val Atk: Int,val Succ: Int, val Crit:Int) {
    class Result(Fail: Int, Succ: Int,Crit: Int, Prob: Number);
    fun Roll(): List<Result> {
        val res = mutableListOf<Result>()
        for ( ifail in 0 .. Atk) {
            for ( isucc in 0 .. Atk-ifail) {
                val resline = Result(ifail,isucc,Atk-ifail-isucc,0)
                res.add(resline)
            }
        }
        return res;
    }
    class RollCombination(val Nums: IntArray,val Times: Number)

    fun Combinations(): List<RollCombination> {
        var res = mutableListOf<RollCombination>()
        findCombinations(res,0,IntArray(Atk),1,0)
        return res
    }

    private fun findCombinations(res: MutableList<BaseDice.RollCombination>, possToFill: Int, soFar: IntArray, mult: Int, nsf: Int) {
        if ( possToFill == soFar.size ) {
            res.add(RollCombination(soFar.clone(),mult))
        } else {
            var top = if ( possToFill == 0 ) 6 else soFar[possToFill-1]
            val omult = mult * (Atk-possToFill)
            for ( i in 1 .. top ) {
                soFar[possToFill] = i
                if ( possToFill > 0 && i == soFar[possToFill-1])
                    findCombinations(res,possToFill+1,soFar,omult/(nsf+1),nsf+1)
                else
                    findCombinations(res,possToFill+1,soFar,omult,1)
            }
        }
    }
}