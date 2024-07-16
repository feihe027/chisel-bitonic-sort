package bitonicsort

import chisel3._
import chiseltest._
import chisel3.util._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random
import chiseltest.internal.ForkBuilder


class BitonicSortTest extends AnyFlatSpec with ChiselScalatestTester {
    val TEST_LENGTH = 128
    val PIPELINE_FRAC = 4

    "BitonicSort Test" should "pass" in {
        test(new BitonicSort(TEST_LENGTH, 10, UInt(10.W), PIPELINE_FRAC))
        .withAnnotations(Seq(WriteVcdAnnotation))  { dut => 
                val rand = new Random()
                val input = Seq.fill(TEST_LENGTH)((rand.nextInt(1024)))
                val inputSorted = input.sortBy(x => x)
                val tag = rand.nextInt(255) + 1
                dut.io.tagIn.poke(tag.U)
                for(i <- 0 until TEST_LENGTH){
                    dut.io.numberIn(i).poke(input(i).U)
                    dut.io.payloadIn(i).poke(((input(i) + 514) % 1024).U)
                }
                var delay = 0
                while(dut.io.tagOut.peek().litValue.intValue != tag){
                    dut.clock.step(1)
                    delay += 1
                }
                println(s"[BitonicSort Test] Delay = ${delay}")
                for(i <- 0 until TEST_LENGTH){
                    dut.io.numberOut(i).expect(inputSorted(i).U)
                    dut.io.payloadOut(i).expect(((inputSorted(i) + 514) % 1024).U)
                }
        }
    }
}