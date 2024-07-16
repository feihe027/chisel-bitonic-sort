SBT = sbt

VCS = vcs -full64 -sverilog -timescale=1ns/1ns 	+v2k -debug_access+all -kdb -lca  hdl/Cmm.v hdl/HdmDecoder.v hdl/SyncSRam.v tb/tb.sv

# Generate Verilog code
run:
	sbt "runMain cmm.cmmMain"
	@sed -i 's/\/\/ @.*//g' hdl/*.v

.PHONY: clean test wave comp verdi sim vlt vlt_wave
clean:
	@rm -rf verdiLog
	@rm -rf project
	@rm -rf obj_dir
	@rm -rf logs
	@rm -rf hdl
	@rm -rf generated
	@rm -rf csrc
	@rm -rf target
	@rm -rf simv.daidir
	@rm -f simv ucli.key novas_dump.log *.fsdb *.vcd *.sv *.v novas.* 
	@rm -rf test_run_dir
	@rm -f *.fir *_obj *.anno.json

test:
	$(SBT) "testOnly cmm.TopTest"

wave:
	gtkwave test_run_dir/TopTestBench_should_pass/Cmm.vcd

comp:
	$(VCS) 

verdi:
	verdi -ssf rtl.fsdb -nologo
 
sim:
	./simv
	
vlt:
	verilator -cc --exe -x-assign fast -Wall --trace --assert --coverage -f input.vc -sv verilator_cpp/top.sv  hdl/YsCounter.v verilator_cpp/sim_main.cpp
	make -j -C obj_dir -f ../Makefile_obj
	@echo "------------- RUN Verilagot Sim  -------------------"
	@rm -rf logs
	@mkdir -p logs
	obj_dir/Vtop +trace

vlt_wave:
	gtkwave logs/vlt_dump.vcd