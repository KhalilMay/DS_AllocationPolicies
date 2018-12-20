/**
 * A [FairnessSelectionPolicy] that selects the machine using a Fairness allocation algorithm: select the machine with
 * the with the same amount of available cores so every tasks gets fair amount.
 */
class FairnessPolicy : MachineSelectionPolicy {
    override suspend fun select(machines: List<Machine>, task: Task): Machine? =
        context<StageScheduler.State, OdcModel>().run {
		var fair = 0
		var i = 0
		if(machines.size != 0)
		{
		var taskForMachine = state.tasks.size/machines.size
		if(taskForMachine == 0)
		{
		taskForMachine = 1;
		}
		machines.forEach{
			fair = (state.machineCores[it] ?: 0)/taskForMachine
		}
		
		}
		machines.sortedByDescending { abs(fair - (state.machineCores[it] ?: 0)) }.firstOrNull()
		
        }
}


/**
* DRFPolicy
*/
class DRFSortingPolicy: TaskSortingPolicy {
    override suspend fun sort(tasks: List<Task>): List<Task> = tasks.sortedBy{it.flops}
}