<template>
	<div>
		<p>Complete tasks: {{todos.filter(todo => {return todo.done === true}).length }}</p>
		<p>Pending tasks: {{todos.filter(todo => {return todo.done === false}).length }}</p>
		<div class="card text-center" v-for="todo in todos">
			<div class="content">
				<div class="header">{{ todo.title }}</div>
				<div>
					<span class="right">
						<i class="fa fa-pencil-square-o"></i>
					</span>
				</div>
				<div class="green button" v-show="todo.done">
					Completed
				</div>
				<div class="red button" v-show="todo.done">
					Complete
				</div>
			</div>
		</div>
	</div>
</template>

<script type = "text/javascript">
	export default {
		
	}
</script>

<style lang='sass'>
	.card
		margin: 10px 0
	.content
		border: 1px solid #000
		border-radius: 4px
	.right
		float: right
	.green
		color: green
	.red
		color: red

</style>